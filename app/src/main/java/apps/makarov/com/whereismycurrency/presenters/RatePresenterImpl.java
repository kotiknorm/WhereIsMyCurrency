package apps.makarov.com.whereismycurrency.presenters;

import android.util.Log;

import java.util.List;

import apps.makarov.com.whereismycurrency.models.Bank;
import apps.makarov.com.whereismycurrency.models.Rate;
import apps.makarov.com.whereismycurrency.net.WimcService;
import apps.makarov.com.whereismycurrency.view.iviews.RateView;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by makarov on 26/06/15.
 */

public class RatePresenterImpl implements RatePresenter {

    private static final String TAG = "RatePresenterImpl";

    private RateView mRateView;
    private Subscription mGetHotAnthologySubscription;
    private WimcService mWimcService;
    private static Observable<List<Bank>> mGetHotAnthologyObservable;

    private Observer<List<Bank>> mGetHotAnthologyObserver = new Observer<List<Bank>>() {
        @Override
        public void onCompleted() {
            Log.d(TAG, "onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "onError", e);
            mRateView.setValue(0, 1);
        }

        @Override
        public void onNext(List<Bank> list) {
            Log.d(TAG, "onNext");

            Bank firstBank = list.get(0);
            Rate usdRate = firstBank.getRates().get(0);

            mRateView.setValue(usdRate.getBuy(), usdRate.getSell());
        }
    };

    public RatePresenterImpl(RateView hotView, WimcService wimcService) {
        this.mRateView = hotView;
        this.mWimcService = wimcService;
    }

    @Override
    public void onResume() {
        pullAllBankFromNetwork();
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

    private void pullAllBankFromNetwork() {
        if (mGetHotAnthologySubscription != null) {
            mGetHotAnthologySubscription.unsubscribe();
            mGetHotAnthologySubscription = null;
        }

        mGetHotAnthologyObservable = mWimcService
                .getAllBank()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache();

        mGetHotAnthologySubscription = mGetHotAnthologyObservable

                .subscribe(mGetHotAnthologyObserver);
    }
}
