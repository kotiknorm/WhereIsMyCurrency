package apps.makarov.com.whereismycurrency.net;

import com.squareup.okhttp.OkHttpClient;

import java.util.Date;
import java.util.List;

import apps.makarov.com.whereismycurrency.DateUtils;
import apps.makarov.com.whereismycurrency.repository.IRepository;
import apps.makarov.com.whereismycurrency.models.CurrencyPair;
import apps.makarov.com.whereismycurrency.models.Bank;
import apps.makarov.com.whereismycurrency.models.Rate;
import apps.makarov.com.whereismycurrency.models.ResultOperation;
import apps.makarov.com.whereismycurrency.models.UserHistory;
import apps.makarov.com.whereismycurrency.net.requests.BankRequest;
import apps.makarov.com.whereismycurrency.net.requests.FixerRequest;
import apps.makarov.com.whereismycurrency.net.requests.WimcRequest;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by makarov on 26/06/15.
 */

public class WimcServiceImpl extends RequestService implements WimcService {

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
    public Observable<List<Rate>> getHistoryRates(final CurrencyPair currencyPair, final Date date) {
        final WimcRequest bankRequest = new FixerRequest(currencyPair.getBaseCurrency(), date);

        Observable<List<Rate>> localStoreObservable = Observable.create(new Observable.OnSubscribe<List<Rate>>() {
            @Override
            public void call(Subscriber<? super List<Rate>> subscriber) {
                try {
                    List<Rate> list = getStore().getRates(currencyPair, date, Bank.DEFAULT);

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
                    List<Rate> list = getStore().getRates(currencyPair, DateUtils.getTodayDate(), Bank.DEFAULT);

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
        userRate.setKey(Rate.generateKey(userRate));

        userHistory.setDate(date);
        userHistory.setValue(summa);
        userHistory.setRate(userRate);
        userHistory.setKey(UserHistory.generateKey(userHistory));

        getStore().saveObject(userHistory);
        return userHistory;
    }

    @Override
    public ResultOperation addResult(Rate rate, UserHistory userHistory) {
        ResultOperation result = new ResultOperation();
        result.setUserHistory(userHistory);
        result.setExitRate(rate);
        result.setDate(userHistory.getDate());
        result.setKey(ResultOperation.generateKey(result));
        getStore().saveObject(result);
        return result;
    }

    @Override
    public ResultOperation getResultOperation(String key) {
        return getStore().getResultOperation(key);
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

