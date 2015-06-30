package apps.makarov.com.whereismycurrency.net;

import com.squareup.okhttp.OkHttpClient;

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

/**
 * Created by makarov on 26/06/15.
 */

public class WimcServiceImpl extends RequestService implements WimcService {

    public WimcServiceImpl(OkHttpClient client, IStore store) {
        super(client, store);
    }

    @Override
    public Observable<List<Bank>> getBanks() {
        final WimcRequest bankRequest = new BankRequest();

        Observable<List<Bank>> localStoreObservable = Observable.create(new Observable.OnSubscribe<List<Bank>>() {
            @Override
            public void call(Subscriber<? super List<Bank>> subscriber) {
                try {
                    List<Bank> list = mStore.getBanks();

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
    public Observable<List<UserHistory>> getHistory() {
        return Observable.create(new Observable.OnSubscribe<List<UserHistory>>() {
            @Override
            public void call(Subscriber<? super List<UserHistory>> subscriber) {
                try {
                    List<UserHistory> list = mStore.getUserHistory();

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
    public Observable<List<Rate>> getRates(final String baseCurrency, final String compareCurrency, final Date date, final String bankName) {
        final WimcRequest bankRequest = getRateRequest(date, baseCurrency);

        Observable<List<Rate>> localStoreObservable = Observable.create(new Observable.OnSubscribe<List<Rate>>() {
            @Override
            public void call(Subscriber<? super List<Rate>> subscriber) {
                try {
                    List<Rate> list = mStore.getRates(baseCurrency, compareCurrency, date, bankName);

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

        mStore.saveObject(userHistory);
    }

    private WimcRequest getRateRequest(Date date, String baseCurrency) {
        if (DateUtils.isToday(date))
            return new BankRequest();
        else
            return new FixerRequest(baseCurrency, date);
    }

}

