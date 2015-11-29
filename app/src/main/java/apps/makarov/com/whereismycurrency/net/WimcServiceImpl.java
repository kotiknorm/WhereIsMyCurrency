package apps.makarov.com.whereismycurrency.net;

import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import apps.makarov.com.whereismycurrency.repository.protocols.RateProtocol;
import apps.makarov.com.whereismycurrency.repository.realm.models.CurrencyPairRealm;
import apps.makarov.com.whereismycurrency.repository.realm.models.ResultOperationRealm;
import apps.makarov.com.whereismycurrency.repository.protocols.ResultOperationProtocol;
import io.realm.RealmObject;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

//import apps.makarov.com.whereismycurrency.repository.realm.models.Bank;

/**
 * Created by makarov on 26/06/15.
 */

public class WimcServiceImpl extends RequestService implements WimcService {

    protected BankRealmMapper mBankRealmMapper = new BankRealmMapper();
    protected ResultOperationRealmMapper mResultMapper = new ResultOperationRealmMapper();
    protected RateRealmMapper mRateRealmMapper = new RateRealmMapper();
    protected CurrencyPairRealmMapper mCurrencyPairRealmMapper = new CurrencyPairRealmMapper();
    protected UserHistoryRealmMapper mUserHistoryRealmMapper = new UserHistoryRealmMapper();

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
                        resultList.add(mResultMapper.dataToModel(item));
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
                    CurrencyPairRealm pair = mCurrencyPairRealmMapper.modelToData(currencyPair);

                    List<RateProtocol> list = getStore().getRates(pair, date, Bank.DEFAULT);
                    List<Rate> resultList = new ArrayList<>(list.size());
                    for (RateProtocol item : list) {
                        resultList.add(mRateRealmMapper.dataToModel(item));
                    }

                    subscriber.onNext(resultList);
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread());

        Func1<List, List> func = new Func1<List, List>() {
            @Override
            public List call(List rates) {

                List<RealmObject> resultList = new ArrayList<>(rates.size());
                for (Object item : rates) {
                    resultList.add(mRateRealmMapper.modelToData((Rate) item));
                }

                observableSaveObjects(resultList);

                return rates;
            }
        };

        return getObservableRequest(bankRequest, localStoreObservable, func);
    }

    @Override
    public Observable<List<Rate>> getAllRatesByCurrencyPair(final CurrencyPair currencyPair){

        return Observable.create(new Observable.OnSubscribe<List<Rate>>() {
            @Override
            public void call(Subscriber<? super List<Rate>> subscriber) {
                try {
                    CurrencyPairRealm pair = mCurrencyPairRealmMapper.modelToData(currencyPair);

                    List<RateProtocol> list = getStore().getRatesByCurrencyPair(pair, DateUtils.getTodayDate());
                    List<Rate> resultList = new ArrayList<>(list.size());
                    for (RateProtocol item : list) {
                        resultList.add(mRateRealmMapper.dataToModel(item));
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
    public Observable<List<Rate>> getRatesAllBank(final CurrencyPair currencyPair) {
        final WimcRequest bankRequest = new BankRequest();

        final Observable<List<Rate>> localStoreObservable = Observable.create(new Observable.OnSubscribe<List<Rate>>() {
            @Override
            public void call(Subscriber<? super List<Rate>> subscriber) {
                try {
//                    getStore().addUrlToCache(bankRequest.getRequest().urlString());
                    CurrencyPairRealm pair = mCurrencyPairRealmMapper.modelToData(currencyPair);

                    List<RateProtocol> list = getStore().getRates(pair, DateUtils.getTodayDate(), Bank.DEFAULT);
                    List<Rate> resultList = new ArrayList<>(list.size());
                    for (RateProtocol item : list) {
                        resultList.add(mRateRealmMapper.dataToModel(item));
                    }

                    subscriber.onNext(resultList);
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread());

        Func1<List, List> func = new Func1<List, List>() {
            @Override
            public List call(List rates) {

                List<RealmObject> resultList = new ArrayList<>(rates.size());
                for (Object item : rates) {
                    resultList.add(mBankRealmMapper.modelToData((Bank) item));
                }

                observableSaveObjects(resultList);

                return rates;
            }
        };

        return getObservableRequest(bankRequest, localStoreObservable, func);
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

        getStore().saveObject(mUserHistoryRealmMapper.modelToData(userHistory));
        return userHistory;
    }

    @Override
    public ResultOperation addResult(Rate rate, UserHistory userHistory) {
        ResultOperation result = new ResultOperation();
        result.setUserHistory(userHistory);
        result.setExitRate(rate);
        result.setDate(userHistory.getDate());
        result.setIsHistory(false);

        ResultOperationProtocol resultRealm = mResultMapper.modelToData(result);

        getStore().saveObject(resultRealm);
        return result;
    }

    @Override
    public ResultOperation addResultInHistory(ResultOperation resultOperation) {
        ResultOperationProtocol operation = mResultMapper.modelToData(resultOperation);
        ResultOperationProtocol result = getStore().resultToHistory(operation);
        return mResultMapper.dataToModel(result);
    }

    @Override
    public void removeResult(ResultOperation resultOperation) {
        ResultOperationProtocol operation = mResultMapper.modelToData(resultOperation);
        getStore().removeResult(operation);
    }

    @Override
    public ResultOperation getResultOperation(String key) {
        ResultOperationProtocol operation = getStore().getResultOperation(key);
        ResultOperation operationData = mResultMapper.dataToModel(operation);
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


    private Observable<Exception> observableSaveRate(final List<Rate> list) {
        return Observable.create(new Observable.OnSubscribe<Exception>() {
            @Override
            public void call(Subscriber<? super Exception> subscriber) {
                try {
                    for (Rate object : list) {
                        getStore().saveObject(object);
                    }
                    subscriber.onNext(null);
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        });
    }


}

