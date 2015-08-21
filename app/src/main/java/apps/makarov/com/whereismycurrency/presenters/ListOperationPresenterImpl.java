package apps.makarov.com.whereismycurrency.presenters;

import android.util.Log;

import java.util.List;

import apps.makarov.com.whereismycurrency.R;
import apps.makarov.com.whereismycurrency.models.ResultOperationData;
import apps.makarov.com.whereismycurrency.net.WimcService;
import apps.makarov.com.whereismycurrency.view.iviews.ListOperationView;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by makarov on 03/07/15.
 */
public class ListOperationPresenterImpl implements ListOperationPresenter {

    private static final String TAG = ListOperationPresenterImpl.class.getSimpleName();

    private ListOperationView mListOperationView;
    private WimcService mWimcService;

    public ListOperationPresenterImpl(ListOperationView listOperationView, WimcService wimcService) {
        this.mListOperationView = listOperationView;
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
        getResultObservable().subscribe(new Observer<List<ResultOperationData>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError", e);
                mListOperationView.hideProgress();
                mListOperationView.showErrorView(mListOperationView.getContext().getString(R.string.error_not_data));

            }

            @Override
            public void onNext(List<ResultOperationData> rates) {
                fillAdapter(rates);
                mListOperationView.hideProgress();
            }
        });
    }

    private Observable<List<ResultOperationData>> getResultObservable() {
        return mWimcService
                .getResultOperations()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache();
    }

    private Observable<List<ResultOperationData>> getUpdateObservable() {
        return mWimcService
                .getUpdateResultOperations()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache();
    }

    @Override
    public void onUpdateResults() {
        getUpdateObservable().subscribe(new Observer<List<ResultOperationData>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(List<ResultOperationData> rates) {
                fillAdapter(rates);
            }
        });
    }

    private void fillAdapter(List<ResultOperationData> rates){
        if(rates.size() == 0){
            mListOperationView.setVisabileSplash(true);
            return;
        }

        mListOperationView.setVisabileSplash(false);
        mListOperationView.showHistoryList(rates);
    }

}
