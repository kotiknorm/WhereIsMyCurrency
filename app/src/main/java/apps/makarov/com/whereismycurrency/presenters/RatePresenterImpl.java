package apps.makarov.com.whereismycurrency.presenters;

import android.util.Log;

import java.util.Date;
import java.util.List;

import apps.makarov.com.whereismycurrency.R;
import apps.makarov.com.whereismycurrency.models.Rate;
import apps.makarov.com.whereismycurrency.models.UserHistory;
import apps.makarov.com.whereismycurrency.net.WimcService;
import apps.makarov.com.whereismycurrency.view.adapters.HistoryAdapter;
import apps.makarov.com.whereismycurrency.view.iviews.RateView;
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

public class RatePresenterImpl implements RatePresenter {

    private static final String TAG = RatePresenterImpl.class.getSimpleName();

    private RateView mRateView;
    private Subscription mGetRateSubscription;
    private Subscription mGetHistorySubscription;
    private WimcService mWimcService;

    private static Observable<Rate> mGetRateObservable;
    private static Observable<List<UserHistory>> mGetHistoryObservable;

    public RatePresenterImpl(RateView hotView, WimcService wimcService) {
        this.mRateView = hotView;
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
        mGetHistoryObservable = getHistoryObservable();

        mGetHistorySubscription = mGetHistoryObservable.subscribe(new Observer<List<UserHistory>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(List<UserHistory> rates) {
                HistoryAdapter historyAdapter = new HistoryAdapter(mRateView.getContext(), rates);
                mRateView.setAdapterForRecyclerView(historyAdapter);
            }
        });

    }

    @Override
    public void enterOperation(String baseCurrency, String compareCurrency, final double value, final double rateValue) {
        mWimcService.addHistoryItem(baseCurrency, compareCurrency, new Date(), value, rateValue);

        mGetRateObservable = getRateObservable(baseCurrency, compareCurrency);

        mGetRateSubscription = mGetRateObservable
                .subscribe(new Observer<Rate>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError", e);
                        mRateView.setResultOperation(e.getMessage());
                    }

                    @Override
                    public void onNext(Rate rate) {
                        Log.d(TAG, "onNext");

                        double valueRate = rate.getValue();
                        double buy = valueRate * value;
                        double factValue = value * rateValue;

                        String result = (buy <= factValue
                                ? mRateView.getContext().getString(R.string.loser_result)
                                : mRateView.getContext().getString(R.string.win_result)) + " " +
                                Math.abs(buy - factValue);

                        mRateView.setResultOperation(result);
                    }
                });
    }

    private Observable<List<UserHistory>> getHistoryObservable(){
        return mWimcService
                .getHistory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache();
    }

    private Observable<Rate> getRateObservable(String baseCurrency, String compareCurrency){
        return mWimcService
                .getRatesAllBank(baseCurrency, compareCurrency)
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

}
