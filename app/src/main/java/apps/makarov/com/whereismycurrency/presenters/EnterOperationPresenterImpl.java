package apps.makarov.com.whereismycurrency.presenters;

import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import apps.makarov.com.whereismycurrency.R;
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

    private String mBaseCurrency;
    private String mCompareCurrency;
    private Date mDate;
    private double mRate = 0;
    private double mBaseValue = 0;
    private double mCompareValue = 0;

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
        onEnterBaseCurrency(Rate.RUB_CODE);
        onEnterCompareCurrency(Rate.EUR_CODE);
        onEnterDateOperation(Calendar.getInstance().getTime());
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

    private boolean isValidData(){
        if (mCompareCurrency == null || mBaseCurrency == null || mDate == null || mBaseValue == 0 || mRate == 0)
            return false;
        return true;
    }

    @Override
    public void onEnterOperation() {
        if (!isValidData()) {
            mEnterOperationView.showErrorView(mEnterOperationView.getContext().getString(R.string.error_not_all_fields));
            return;
        }

        CurrencyPair pair = CurrencyPair.createPair(mBaseCurrency, mCompareCurrency);
        final UserHistory userHistory = mWimcService.addHistoryItem(pair, mDate, mBaseValue, mRate);

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
                        mEnterOperationView.showErrorView(mEnterOperationView.getContext().getString(R.string.error_not_data));

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
    public void onEnterRate(double rate) {
        mRate =  rate;
        correctingValue();
    }

    @Override
    public void onEnterBaseValue(double rate) {
        if(mBaseValue != rate) {
            mBaseValue = rate;
            correctingValue();
        }
    }

    @Override
    public void onEnterCompareValue(double rate) {
        if(rate != mCompareValue) {
            mCompareValue = rate;
            correctingValue();
        }
    }

    @Override
    public void onEnterDateOperation(Date date) {
        mDate = date;
        mEnterOperationView.setDateView(date);
        processOldRate();
    }

    @Override
    public void onEnterCompareCurrency(String currency) {
        Drawable iconDrawable = Rate.getCurrencyIcon(mEnterOperationView.getContext(), currency);
        String nameCurrency = Rate.getCurrencyName(mEnterOperationView.getContext(), currency);

        mCompareCurrency = currency;
        mEnterOperationView.setCompareCurrency(nameCurrency, iconDrawable);

        processOldRate();
    }

    @Override
    public void onEnterBaseCurrency(String currency) {
        Drawable iconDrawable = Rate.getCurrencyIcon(mEnterOperationView.getContext(), currency);
        String nameCurrency = Rate.getCurrencyName(mEnterOperationView.getContext(), currency);

        mBaseCurrency = currency;
        mEnterOperationView.setBaseCurrency(nameCurrency, iconDrawable);

        processOldRate();
    }

    @Override
    public void updateResults(Date date) {
        // взять курс по дате и обновить всю историю с exitRete этого курса
    }

    @Override
    public void onOpenCompareCurrencyDialog() {
        mEnterOperationView.setSellCurrencyList(new CurrencyAdapter(mEnterOperationView.getContext(), Rate.getCodesList()));
    }

    @Override
    public void onOpenBaseCurrencyDialog() {
        mEnterOperationView.setBuyCurrencyList(new CurrencyAdapter(mEnterOperationView.getContext(), Rate.getCodesList()));

    }

    @Override
    public void correctingValue() {
        if(mBaseValue != 0 && mRate != 0){
            mCompareValue = mBaseValue * mRate;
            mEnterOperationView.setCompareValue(mCompareValue);
        }

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
        if (mCompareCurrency == null || mBaseCurrency == null || mDate == null)
            return;

        CurrencyPair pair = CurrencyPair.createPair(mBaseCurrency, mCompareCurrency);

        mGetOldRateObservable = getOldRateObservable(pair, mDate);
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
