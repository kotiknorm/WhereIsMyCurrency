package apps.makarov.com.whereismycurrency.presenters;

import java.util.List;

import apps.makarov.com.whereismycurrency.models.ResultOperation;
import apps.makarov.com.whereismycurrency.net.WimcService;
import apps.makarov.com.whereismycurrency.view.adapters.HistoryAdapter;
import apps.makarov.com.whereismycurrency.view.iviews.ListOperationView;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by makarov on 03/07/15.
 */
public class ListOperationPresenterImpl implements ListOperationPresenter {

    private static final String TAG = ListOperationPresenterImpl.class.getSimpleName();

    private ListOperationView mListOperationView;
    private WimcService mWimcService;

    private Subscription mGetHistorySubscription;
    private static Observable<List<ResultOperation>> mGetHistoryObservable;

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

        mGetHistoryObservable = getHistoryObservable();
        mGetHistorySubscription = mGetHistoryObservable.subscribe(new Observer<List<ResultOperation>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(List<ResultOperation> rates) {
                HistoryAdapter historyAdapter = new HistoryAdapter(rates);
                historyAdapter.setListener(new HistoryAdapter.OnClickToPresenter() {
                    @Override
                    public void onClick(ResultOperation operation) {
                        mListOperationView.showResultOperation(operation.getKey());
                    }
                });
                mListOperationView.setAdapterForRecyclerView(historyAdapter);
            }
        });
    }

    private Observable<List<ResultOperation>> getHistoryObservable() {
        return mWimcService
                .getResultOperations()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache();
    }

}
