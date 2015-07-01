package apps.makarov.com.whereismycurrency.presenters;

import apps.makarov.com.whereismycurrency.R;
import apps.makarov.com.whereismycurrency.models.Rate;
import apps.makarov.com.whereismycurrency.models.ResultOperation;
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
        ResultOperation resultOperation = mWimcService.getResultOperation(resultKey);
        Rate rate = resultOperation.getExitRate();
        double value = resultOperation.getUserHistory().getValue();
        double rateValue = resultOperation.getUserHistory().getRate().getValue();

        double buy = value * rate.getValue();
        double factValue = value * rateValue;

        String resultStr = (buy <= factValue
                ? mResultView.getContext().getString(R.string.loser_result)
                : mResultView.getContext().getString(R.string.win_result)) + " " +
                Math.abs(buy - factValue);
        mResultView.showResultOperation(resultStr);
    }
}
