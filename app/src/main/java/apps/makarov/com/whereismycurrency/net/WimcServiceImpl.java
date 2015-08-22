package apps.makarov.com.whereismycurrency.net;

import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import apps.makarov.com.whereismycurrency.DateUtils;
import apps.makarov.com.whereismycurrency.mappers.realm.BankRealmMapper;
import apps.makarov.com.whereismycurrency.mappers.realm.CurrencyPairRealmMapper;
import apps.makarov.com.whereismycurrency.mappers.realm.RateRealmMapper;
import apps.makarov.com.whereismycurrency.mappers.realm.ResultOperationRealmMapper;
import apps.makarov.com.whereismycurrency.mappers.realm.UserHistoryRealmMapper;
import apps.makarov.com.whereismycurrency.models.Bank;
import apps.makarov.com.whereismycurrency.models.CurrencyPair;
import apps.makarov.com.whereismycurrency.models.Rate;
import apps.makarov.com.whereismycurrency.models.ResultOperation;
import apps.makarov.com.whereismycurrency.models.UserHistory;
import apps.makarov.com.whereismycurrency.net.requests.BankRequest;
import apps.makarov.com.whereismycurrency.net.requests.FixerRequest;
import apps.makarov.com.whereismycurrency.net.requests.WimcRequest;
import apps.makarov.com.whereismycurrency.repository.IRepository;
import apps.makarov.com.whereismycurrency.repository.models.CurrencyPairRealm;
import apps.makarov.com.whereismycurrency.repository.models.ResultOperationRealm;
import apps.makarov.com.whereismycurrency.repository.protocols.ResultOperationProtocol;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

//import apps.makarov.com.whereismycurrency.repository.models.Bank;

/**
 * Created by makarov on 26/06/15.
 */

public class WimcServiceImpl extends RequestService implements WimcService {

    @Inject
    public BankRealmMapper bankRealmMapper;
    @Inject
    public ResultOperationRealmMapper resultMapper;
    @Inject
    public RateRealmMapper rateRealmMapper;
    @Inject
    public CurrencyPairRealmMapper currencyPairRealmMapper;
    @Inject
    public UserHistoryRealmMapper userHistoryRealmMapper;

    public WimcServiceImpl(OkHttpClient client, IRepository store) {
        super(client, store);
    }

    @Override
    public Observable<List<UserHistory>> getHistory() {
        return Observable.create(new Observable.OnSubscribe<List<UserHistory>>() {
            @Override
            public void call(Subscriber<? super List<UserHistory>> subscriber) {
                try {
                    List<UserHistory> list = getStore().getUserHistory();

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
    public Observable<List<ResultOperation>> getResultOperations() {
        return Observable.create(new Observable.OnSubscribe<List<ResultOperation>>() {
            @Override
            public void call(Subscriber<? super List<ResultOperation>> subscriber) {
                try {
                    List<ResultOperationRealm> list = getStore().getAllResultOperation();
                    List<ResultOperation> resultList = new ArrayList<ResultOperation>(list.size());
                    for(ResultOperationRealm item : list){
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
    public Observable<List<ResultOperation>> getUpdateResultOperations() {
        return Observable.create(new Observable.OnSubscribe<List<ResultOperation>>() {
            @Override
            public void call(Subscriber<? super List<ResultOperation>> subscriber) {
                try {
                    List<ResultOperation> list = getStore().getAllResultOperation();

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
    public Observable<List<Rate>> getRates(final CurrencyPair currencyPair, final Date date) {
        final WimcRequest bankRequest = new FixerRequest(currencyPair.getBaseCurrency(), date);

        Observable<List<Rate>> localStoreObservable = Observable.create(new Observable.OnSubscribe<List<Rate>>() {
            @Override
            public void call(Subscriber<? super List<Rate>> subscriber) {
                try {
                    CurrencyPairRealm pair = currencyPairRealmMapper.dataToModel(currencyPair);
                    List<Rate> list = getStore().getRates(pair, date, Bank.DEFAULT);

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
    public Observable<List<Rate>> getRatesAllBank(final CurrencyPair currencyPair) {
        final WimcRequest bankRequest = new BankRequest();

        Observable<List<Rate>> localStoreObservable = Observable.create(new Observable.OnSubscribe<List<Rate>>() {
            @Override
            public void call(Subscriber<? super List<Rate>> subscriber) {
                try {
//                    getStore().addUrlToCache(bankRequest.getRequest().urlString());
                    CurrencyPairRealm pair = currencyPairRealmMapper.dataToModel(currencyPair);
                    List<Rate> list = getStore().getRates(pair, DateUtils.getTodayDate(), Bank.DEFAULT);

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
    public UserHistory addHistoryItem(final CurrencyPair currencyPair,  final Date date, double summa, double rateValue) {
        UserHistory userHistory = new UserHistory();

        Rate userRate = new Rate();
        userRate.setCurrencyPair(currencyPair);
        userRate.setValue(rateValue);
        userRate.setBank(Bank.USER_RATE);

        userHistory.setDate(date);
        userHistory.setValue(summa);
        userHistory.setRate(userRate);

        getStore().saveObject(userHistoryRealmMapper.dataToModel(userHistory));
        return userHistory;
    }

    @Override
    public ResultOperation addResult(Rate rate, UserHistory userHistory) {
        ResultOperation result = new ResultOperation();
        result.setUserHistory(userHistory);
        result.setExitRate(rate);
        result.setDate(userHistory.getDate());
        getStore().saveObject(result);
        return result;
    }

    @Override
    public ResultOperation addResultInHistory(ResultOperation resultOperation) {
        ResultOperationProtocol operation = resultMapper.dataToModel(resultOperation);
        ResultOperationProtocol result = getStore().resultToHistory(operation);
        return resultMapper.modelToData(result);
    }

    @Override
    public void removeResult(ResultOperation resultOperation) {
        ResultOperationProtocol operation = resultMapper.dataToModel(resultOperation);
        getStore().removeResult(operation);
    }

    @Override
    public ResultOperation getResultOperation(String key) {
        ResultOperationProtocol operation = getStore().getResultOperation(key);
        ResultOperation operationData = resultMapper.modelToData(operation);
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

