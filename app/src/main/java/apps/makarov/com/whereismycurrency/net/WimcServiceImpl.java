package apps.makarov.com.whereismycurrency.net;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;

import java.util.Date;
import java.util.List;

import apps.makarov.com.whereismycurrency.DateUtils;
import apps.makarov.com.whereismycurrency.database.IStore;
import apps.makarov.com.whereismycurrency.models.Bank;
import apps.makarov.com.whereismycurrency.models.Rate;
import apps.makarov.com.whereismycurrency.models.UserHistory;
import apps.makarov.com.whereismycurrency.net.requests.BankRequest;
import apps.makarov.com.whereismycurrency.net.requests.FixerRequest;
import apps.makarov.com.whereismycurrency.net.requests.WimcRequest;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by makarov on 26/06/15.
 */

public class WimcServiceImpl extends RequestService implements WimcService {

    public WimcServiceImpl(OkHttpClient client, IStore store) {
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
    public Observable<List<Rate>> getHistoryRates(final String baseCurrency, final String compareCurrency, final Date date) {
        final WimcRequest bankRequest = new FixerRequest(baseCurrency, date);

        Observable<List<Rate>> localStoreObservable = Observable.create(new Observable.OnSubscribe<List<Rate>>() {
            @Override
            public void call(Subscriber<? super List<Rate>> subscriber) {
                try {
                    List<Rate> list = getStore().getRates(baseCurrency, compareCurrency, date, Bank.DEFAULT);

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
    public Observable<List<Rate>> getRatesAllBank(final String baseCurrency, final String compareCurrency) {
        final WimcRequest bankRequest = new BankRequest();

        Observable<List<Rate>> localStoreObservable = Observable.create(new Observable.OnSubscribe<List<Rate>>() {
            @Override
            public void call(Subscriber<? super List<Rate>> subscriber) {
                try {
                    List<Rate> list = getStore().getRates(baseCurrency, compareCurrency, DateUtils.getTodayDate(), Bank.DEFAULT);

                    subscriber.onNext(list);
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread());

        return getObservableRequest(bankRequest, localStoreObservable).doOnNext(cachingRequest());
    }

    @Override
    public void addHistoryItem(final String baseCurrency, final String compareCurrency, final Date date, double rateValue, double value) {
        UserHistory userHistory = new UserHistory();

        Rate userRate = new Rate();
        userRate.setCompareCurrency(compareCurrency);
        userRate.setBaseCurrency(baseCurrency);
        userRate.setValue(rateValue);
        userRate.setBank(Bank.USER_RATE);
        userRate.setKey(Rate.generateKey(userRate));

        userHistory.setDate(date);
        userHistory.setValue(value);
        userHistory.setRate(userRate);
        userHistory.setKey(UserHistory.generateKey(userHistory));

        getStore().saveObject(userHistory);
    }

    protected Action1 cachingRequest() {
        return new Action1<Response>() {
            @Override
            public void call(Response response) {
                getStore().addUrlToCache(response.request().urlString());
            }
        };
    }

}

