package apps.makarov.com.whereismycurrency.presenters;

import apps.makarov.com.whereismycurrency.R;
import apps.makarov.com.whereismycurrency.ResultUtils;
import apps.makarov.com.whereismycurrency.models.ResultOperationData;
import apps.makarov.com.whereismycurrency.net.WimcService;
import apps.makarov.com.whereismycurrency.view.iviews.ResultView;

/**
 * Created by makarov on 01/07/15.
 */
public class ResultPresenterImpl implements ResultPresenter {

    private ResultView mResultView;
    private WimcService mWimcService;

    private ResultOperationData mResultOperation;

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
    public void setUniqueOperation(String key) {
        mResultOperation = mWimcService.getResultOperation(key);

        double diff = ResultUtils.getDiff(mResultOperation);
        mResultView.setDiffValue(diff > 0 ? ("+" + diff) : (diff + ""));

        if(diff > 0){
            mResultView.setResultColor(R.color.positive_color);
        }else{
            mResultView.setResultColor(R.color.negative_color);
        }

        mResultView.setVisibileHistoryBtn(mResultOperation.isHistory() ? false : true);

        CurrencyPairResultPresenter openPresenter = new CurrencyPairResultPresenterImpl
                (mResultOperation.getUserHistory().getRate(), mResultOperation.getUserHistory().getValue(), "Открытие операции");
        CurrencyPairResultPresenter exitPresenter = new CurrencyPairResultPresenterImpl
                (mResultOperation.getExitRate(), ResultUtils.getFinishFirstOperationValue(mResultOperation), "Закрытие операции");

        mResultView.addOpenOperationResult(openPresenter);
        mResultView.addExitOperationResult(exitPresenter);

    }

    @Override
    public void addResultInHistory() {
        mResultOperation = mWimcService.addResultInHistory(mResultOperation);
        mResultView.onAddedResultToHistory();
    }

    @Override
    public void removeResult() {
        mWimcService.removeResult(mResultOperation);
        mResultView.onRemovedResult();
    }


}
