package apps.makarov.com.whereismycurrency.presenters;

import android.util.Log;

import java.util.List;

import apps.makarov.com.whereismycurrency.models.CurrencyPair;
import apps.makarov.com.whereismycurrency.models.Rate;
import apps.makarov.com.whereismycurrency.models.ResultOperation;
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
    private String mKey;
    private ResultOperation mHistory;

    public BanksListPresenterImpl(BanksView banksView, WimcService wimcService) {
        this.mBanksView = banksView;
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
    public void onRefresh() {

        CurrencyPair pair = mHistory.getExitRate().getCurrencyPair();

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
        mKey = resultKey;
        mHistory = mWimcService.getResultOperation(mKey);

        mBanksView.bindModelToView(mHistory);

        //onRefresh();
        //fillAdapter(list);
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
