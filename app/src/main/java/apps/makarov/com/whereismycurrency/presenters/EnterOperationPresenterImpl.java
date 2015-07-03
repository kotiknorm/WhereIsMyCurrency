package apps.makarov.com.whereismycurrency.presenters;

import android.util.Log;

import java.util.Date;
import java.util.List;

import apps.makarov.com.whereismycurrency.models.CurrencyPair;
import apps.makarov.com.whereismycurrency.models.Rate;
import apps.makarov.com.whereismycurrency.models.ResultOperation;
import apps.makarov.com.whereismycurrency.models.UserHistory;
import apps.makarov.com.whereismycurrency.net.WimcService;
import apps.makarov.com.whereismycurrency.view.adapters.CurrencyAdapter;
import apps.makarov.com.whereismycurrency.view.iviews.EnterOperationView;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by makarov on 26/06/15.
 */

public class EnterOperationPresenterImpl implements EnterOperationPresenter {

    private static final String TAG = EnterOperationPresenterImpl.class.getSimpleName();

    private EnterOperationView mEnterOperationView;
    private WimcService mWimcService;

    private CurrencyPair mCurrencyPair;
    private Date mDate;

    private Subscription mGetRateSubscription;
    private Subscription mGetOldRateSubscription;

    private static Observable<Rate> mGetRateObservable;
    private static Observable<Rate> mGetOldRateObservable;

    public EnterOperationPresenterImpl(EnterOperationView hotView, WimcService wimcService) {
        this.mEnterOperationView = hotView;
        this.mWimcService = wimcService;
    }

    @Override
    public void onResume() {
        onRefresh();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onRefresh() {
    }

    @Override
    public void onEnterOperation(String baseCurrency, String compareCurrency, final double summa, final double rateValue) {
        CurrencyPair pair = CurrencyPair.createPair(compareCurrency, baseCurrency);
        final UserHistory userHistory = mWimcService.addHistoryItem(pair, new Date(), summa, rateValue);

        mGetRateObservable = getRateObservable(pair);

        mGetRateSubscription = mGetRateObservable
                .subscribe(new Observer<Rate>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError", e);
                    }

                    @Override
                    public void onNext(Rate rate) {
                        Log.d(TAG, "onNext");

                        ResultOperation result = mWimcService.addResult(rate, userHistory);
                        String key = result.getKey();
                        mEnterOperationView.addOperation(key);
                    }
                });
    }

    @Override
    public void onEnterDateOperation(Date date) {
        mDate = date;
        processOldRate();
    }

    @Override
    public void onEnterCurrencyPair(CurrencyPair pair) {
        mCurrencyPair = pair;
        processOldRate();
    }

    @Override
    public void onProcessLoadCurrencyPairs() {
        mEnterOperationView.setCurrencyPairList(new CurrencyAdapter(mEnterOperationView.getContext(), Rate.getPairCodesList()));
    }

    private Observable<Rate> getOldRateObservable(CurrencyPair currencyPair, Date date) {
        return mWimcService
                .getHistoryRates(currencyPair, date)
                .flatMap(new Func1<List<Rate>, Observable<Rate>>() {
                    @Override
                    public Observable<Rate> call(final List<Rate> rates) {
                        return Observable.create(new Observable.OnSubscribe<Rate>() {
                            @Override
                            public void call(Subscriber<? super Rate> subscriber) {
                                try {
                                    // first rate
                                    subscriber.onNext(rates.get(0));
                                    subscriber.onCompleted();

                                } catch (Throwable e) {
                                    subscriber.onError(e);
                                }
                            }
                        });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache();
    }

    private Observable<Rate> getRateObservable(CurrencyPair currencyPair) {
        return mWimcService
                .getRatesAllBank(currencyPair)
                .flatMap(new Func1<List<Rate>, Observable<Rate>>() {
                    @Override
                    public Observable<Rate> call(final List<Rate> rates) {
                        return Observable.create(new Observable.OnSubscribe<Rate>() {
                            @Override
                            public void call(Subscriber<? super Rate> subscriber) {
                                try {
                                    //search min rate
                                    subscriber.onNext(rates.get(0));
                                    subscriber.onCompleted();

                                } catch (Throwable e) {
                                    subscriber.onError(e);
                                }
                            }
                        });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache();
    }

    private void processOldRate() {
        if (mCurrencyPair == null || mDate == null)
            return;


        mGetOldRateObservable = getOldRateObservable(mCurrencyPair, mDate);
        mGetOldRateSubscription = mGetOldRateObservable
                .subscribe(new Observer<Rate>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError", e);
                        mEnterOperationView.setOldRate("");
                    }

                    @Override
                    public void onNext(Rate rate) {
                        Log.d(TAG, "onNext");
                        mEnterOperationView.setOldRate(String.valueOf(rate.getValue()));
                    }
                });
    }

}
