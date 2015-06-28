package apps.makarov.com.whereismycurrency.net;

import com.squareup.okhttp.OkHttpClient;

import java.util.Date;
import java.util.List;

import apps.makarov.com.whereismycurrency.database.IStore;
import apps.makarov.com.whereismycurrency.models.Bank;
import apps.makarov.com.whereismycurrency.models.Rate;
import apps.makarov.com.whereismycurrency.models.UserHistory;
import apps.makarov.com.whereismycurrency.net.requests.BankRequest;
import apps.makarov.com.whereismycurrency.net.requests.HistoryRateRequest;
import apps.makarov.com.whereismycurrency.net.requests.WimcRequest;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by makarov on 26/06/15.
 */

public class WimcServiceImpl extends RequestService implements WimcService {

    public WimcServiceImpl(OkHttpClient client, IStore store) {
        super(client, store);
    }

    @Override
    public Observable<List<Rate>> getRatesFromBank(String bankName) {
        return null;
    }

    @Override
    public Observable<List<Bank>> getAllBank() {
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
    public Observable<Rate> getRate(final String baseCurrency, final String compareCurrency, final Date date) {
        final WimcRequest bankRequest = new HistoryRateRequest();

        Observable<List<Rate>> localStoreObservable = Observable.create(new Observable.OnSubscribe<List<Rate>>() {
            @Override
            public void call(Subscriber<? super List<Rate>> subscriber) {
                try {
                    List<Rate> list = mStore.getRates(baseCurrency, compareCurrency, date, Bank.DEFAULT);

                    subscriber.onNext(list);
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread());

        return getObservableRequest(bankRequest, localStoreObservable).flatMap(new Func1<List<Rate>, Observable<Rate>>() {
            @Override
            public Observable<Rate> call(final List<Rate> rates) {
                return Observable.create(new Observable.OnSubscribe<Rate>() {

                    @Override
                    public void call(Subscriber<? super Rate> subscriber) {
                        try {
                            subscriber.onNext(rates.get(0));
                            subscriber.onCompleted();

                        } catch (Throwable e) {
                            subscriber.onError(e);
                        }
                    }
                });
            }
        });
    }


    @Override
    public void addHistoryItem(UserHistory historyItem) {
        mStore.saveObject(historyItem);
    }



}

