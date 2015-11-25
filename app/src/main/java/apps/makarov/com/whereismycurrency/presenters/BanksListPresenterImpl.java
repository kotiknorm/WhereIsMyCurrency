package apps.makarov.com.whereismycurrency.presenters;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import apps.makarov.com.whereismycurrency.R;
import apps.makarov.com.whereismycurrency.models.CurrencyPair;
import apps.makarov.com.whereismycurrency.models.Rate;
import apps.makarov.com.whereismycurrency.models.UserHistory;
import apps.makarov.com.whereismycurrency.net.WimcService;
import apps.makarov.com.whereismycurrency.view.iviews.BanksView;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by makarov on 04/08/15.
 */
public class BanksListPresenterImpl  implements BanksListPresenter {

    private static final String TAG = ListOperationPresenterImpl.class.getSimpleName();

    private BanksView mBanksView;
    private WimcService mWimcService;

    private Rate mRate;

    public BanksListPresenterImpl(BanksView banksView, WimcService wimcService) {
        this.mBanksView = banksView;
        this.mWimcService = wimcService;
    }

    @Override
    public void onResume(String key) {
        onRefresh(key);
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onRefresh(String key) {

        UserHistory userHistory = mWimcService.getResultOperation(key).getUserHistory();
        CurrencyPair pair = mWimcService.getResultOperation(key).getExitRate().getCurrencyPair();

        getRateObservable(pair).subscribe(new Observer<List<Rate>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError", e);
            }

            @Override
            public void onNext(List<Rate> banks) {
                fillAdapter(banks);
            }
        });
    }

    private Observable<List<Rate>> getRateObservable(CurrencyPair currencyPair) {
        return mWimcService
                .getAllRatesByCurrencyPair(currencyPair)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache();
    }

    @Override
    public void setRate(String resultKey) {
        // здесь вся логика заполнения списка  полей вью элемента
        UserHistory userHistory = mWimcService.getResultOperation(resultKey).getUserHistory();
        mRate = mWimcService.getResultOperation(resultKey).getExitRate();
        CurrencyPair currencyPair = mRate.getCurrencyPair();

        //List<Rate> list = mWimcService.getRatesAllBank(currencyPair);
        List<Rate> list = new ArrayList<>();
        for(int i = 0; i <= 10; i++){
            Rate rate = new Rate();
            rate.setBank("MyBank" + i);
            rate.setCurrencyPair(currencyPair);
            rate.setValue(333.33);
            list.add(rate);
        }
        mBanksView.setAdapterForRecyclerView(list);
    }

    private void fillAdapter(List<Rate> banks){
        if(banks.size() == 0){
            mBanksView.setVisabileSplash(true);
            return;
        }

        mBanksView.setVisabileSplash(false);
        mBanksView.setAdapterForRecyclerView(banks);
    }
}
