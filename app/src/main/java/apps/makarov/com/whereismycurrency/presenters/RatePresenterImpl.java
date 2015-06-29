package apps.makarov.com.whereismycurrency.presenters;

import android.util.Log;

import java.util.List;

import apps.makarov.com.whereismycurrency.R;
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

    private static final String TAG = RatePresenterImpl.class.getSimpleName();

    private RateView mRateView;
    private Subscription mGetRateSubscription;
    private WimcService mWimcService;
    private static Observable<List<Bank>> mGetBankObservable;
    private static Observable<List<Rate>> mGetRateObservable;

    public RatePresenterImpl(RateView hotView, WimcService wimcService) {
        this.mRateView = hotView;
        this.mWimcService = wimcService;
    }

    @Override
    public void onResume() {
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
    public void enterOperation(final double value, final double rate) {
        mGetBankObservable = mWimcService
                .getAllBank()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache();

        mGetRateSubscription = mGetBankObservable
                .subscribe(new Observer<List<Bank>>() {
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
                    public void onNext(List<Bank> list) {
                        Log.d(TAG, "onNext");

                        Rate firstRate = list.get(0).getRates().first();
                        double buy = firstRate.getValue() * value;
                        double factValue = value * rate;

                        String result = (buy <= factValue
                                ? mRateView.getContext().getString(R.string.loser_result)
                                : mRateView.getContext().getString(R.string.win_result)) + " " +
                                Math.abs(buy - factValue);

                        mRateView.setResultOperation(result);
                    }
                });
    }

}
