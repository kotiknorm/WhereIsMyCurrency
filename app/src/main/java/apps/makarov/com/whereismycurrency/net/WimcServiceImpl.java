package apps.makarov.com.whereismycurrency.net;

import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import apps.makarov.com.whereismycurrency.DateUtils;
import apps.makarov.com.whereismycurrency.mappers.BankMapper;
import apps.makarov.com.whereismycurrency.mappers.CurrencyPairMapper;
import apps.makarov.com.whereismycurrency.mappers.RateMapper;
import apps.makarov.com.whereismycurrency.mappers.ResultOperationMapper;
import apps.makarov.com.whereismycurrency.mappers.UserHistoryMapper;
import apps.makarov.com.whereismycurrency.models.BankData;
import apps.makarov.com.whereismycurrency.models.CurrencyPairData;
import apps.makarov.com.whereismycurrency.models.RateData;
import apps.makarov.com.whereismycurrency.models.ResultOperationData;
import apps.makarov.com.whereismycurrency.models.UserHistoryData;
import apps.makarov.com.whereismycurrency.net.requests.BankRequest;
import apps.makarov.com.whereismycurrency.net.requests.FixerRequest;
import apps.makarov.com.whereismycurrency.net.requests.WimcRequest;
import apps.makarov.com.whereismycurrency.repository.IRepository;
import apps.makarov.com.whereismycurrency.repository.models.CurrencyPair;
import apps.makarov.com.whereismycurrency.repository.models.ResultOperation;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

//import apps.makarov.com.whereismycurrency.repository.models.Bank;

/**
 * Created by makarov on 26/06/15.
 */

public class WimcServiceImpl extends RequestService implements WimcService {

    @Inject
    public BankMapper bankMapper;
    @Inject
    public ResultOperationMapper resultMapper;
    @Inject
    public RateMapper rateMapper;
    @Inject
    public CurrencyPairMapper currencyPairMapper;
    @Inject
    public UserHistoryMapper userHistoryMapper;

    public WimcServiceImpl(OkHttpClient client, IRepository store) {
        super(client, store);
    }

    @Override
    public Observable<List<UserHistoryData>> getHistory() {
        return Observable.create(new Observable.OnSubscribe<List<UserHistoryData>>() {
            @Override
            public void call(Subscriber<? super List<UserHistoryData>> subscriber) {
                try {
                    List<UserHistoryData> list = getStore().getUserHistory();

                    subscriber.onNext(list);
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<ResultOperationData>> getResultOperations() {
        return Observable.create(new Observable.OnSubscribe<List<ResultOperationData>>() {
            @Override
            public void call(Subscriber<? super List<ResultOperationData>> subscriber) {
                try {
                    List<ResultOperation> list = getStore().getAllResultOperation();
                    List<ResultOperationData> resultList = new ArrayList<ResultOperationData>(list.size());
                    for(ResultOperation item : list){
                        resultList.add(resultMapper.modelToData(item));
                    }

                    subscriber.onNext(resultList);
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<ResultOperationData>> getUpdateResultOperations() {
        return Observable.create(new Observable.OnSubscribe<List<ResultOperationData>>() {
            @Override
            public void call(Subscriber<? super List<ResultOperationData>> subscriber) {
                try {
                    List<ResultOperationData> list = getStore().getAllResultOperation();

                    subscriber.onNext(list);
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<RateData>> getRates(final CurrencyPairData currencyPair, final Date date) {
        final WimcRequest bankRequest = new FixerRequest(currencyPair.getBaseCurrency(), date);

        Observable<List<RateData>> localStoreObservable = Observable.create(new Observable.OnSubscribe<List<RateData>>() {
            @Override
            public void call(Subscriber<? super List<RateData>> subscriber) {
                try {
                    CurrencyPair pair = currencyPairMapper.dataToModel(currencyPair);
                    List<RateData> list = getStore().getRates(pair, date, BankData.DEFAULT);

                    subscriber.onNext(list);
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread());

        return getObservableRequest(bankRequest, localStoreObservable);
    }

    @Override
    public Observable<List<RateData>> getRatesAllBank(final CurrencyPairData currencyPair) {
        final WimcRequest bankRequest = new BankRequest();

        Observable<List<RateData>> localStoreObservable = Observable.create(new Observable.OnSubscribe<List<RateData>>() {
            @Override
            public void call(Subscriber<? super List<RateData>> subscriber) {
                try {
//                    getStore().addUrlToCache(bankRequest.getRequest().urlString());
                    CurrencyPair pair = currencyPairMapper.dataToModel(currencyPair);
                    List<RateData> list = getStore().getRates(pair, DateUtils.getTodayDate(), BankData.DEFAULT);

                    subscriber.onNext(list);
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread());

        return getObservableRequest(bankRequest, localStoreObservable);
    }

    @Override
    public UserHistoryData addHistoryItem(final CurrencyPairData currencyPair,  final Date date, double summa, double rateValue) {
        UserHistoryData userHistory = new UserHistoryData();

        RateData userRate = new RateData();
        userRate.setCurrencyPair(currencyPair);
        userRate.setValue(rateValue);
        userRate.setBank(BankData.USER_RATE);

        userHistory.setDate(date);
        userHistory.setValue(summa);
        userHistory.setRate(userRate);

        getStore().saveObject(userHistoryMapper.dataToModel(userHistory));
        return userHistory;
    }

    @Override
    public ResultOperationData addResult(RateData rate, UserHistoryData userHistory) {
        ResultOperationData result = new ResultOperationData();
        result.setUserHistory(userHistory);
        result.setExitRate(rate);
        result.setDate(userHistory.getDate());
        getStore().saveObject(result);
        return result;
    }

    @Override
    public ResultOperationData addResultInHistory(ResultOperationData resultOperation) {
        ResultOperation operation = resultMapper.dataToModel(resultOperation);
        ResultOperation result = getStore().resultToHistory(operation);
        return resultMapper.modelToData(result);
    }

    @Override
    public void removeResult(ResultOperationData resultOperation) {
        ResultOperation operation = resultMapper.dataToModel(resultOperation);
        getStore().removeResult(operation);
    }

    @Override
    public ResultOperationData getResultOperation(String key) {
        ResultOperation operation = getStore().getResultOperation(key);
        ResultOperationData operationData = resultMapper.modelToData(operation);
        return operationData;
    }

//    protected Action1 cachingRequest() {
//        return new Action1<Response>() {
//            @Override
//            public void call(Response response) {
//                getStore().addUrlToCache(response.request().urlString());
//            }
//        };
//    }


}

