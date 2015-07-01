package apps.makarov.com.whereismycurrency.presenters;

import apps.makarov.com.whereismycurrency.net.WimcService;
import apps.makarov.com.whereismycurrency.view.iviews.ResultView;

/**
 * Created by makarov on 01/07/15.
 */
public class ResultPresenterImpl implements ResultPresenter {

    private ResultView mResultView;
    private WimcService mWimcService;

    public ResultPresenterImpl(ResultView resultView, WimcService wimcService) {
        this.mResultView = resultView;
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
    public void setResult(String resultKey) {

    }
}
