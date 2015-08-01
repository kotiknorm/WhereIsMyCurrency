package apps.makarov.com.whereismycurrency.presenters;

import android.graphics.drawable.Drawable;

import java.util.Date;

import apps.makarov.com.whereismycurrency.R;
import apps.makarov.com.whereismycurrency.ResultUtils;
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

    private ResultOperation mResultOperation;

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
        mResultOperation = mWimcService.getResultOperation(resultKey);

        double diff = ResultUtils.getDiff(mResultOperation);
        mResultView.setDiffValue(diff);

        if(diff > 0){
            mResultView.setColorForResult(R.color.positive_color);
        }else{
            mResultView.setColorForResult(R.color.negative_color);
        }

        Drawable openBaseIcon = Rate.getCurrencyIcon(mResultView.getContext(), mResultOperation.getUserHistory().getRate().getCurrencyPair().getBaseCurrency());
        Drawable openCompareIcon = Rate.getCurrencyIcon(mResultView.getContext(), mResultOperation.getUserHistory().getRate().getCurrencyPair().getCompareCurrency());

        mResultView.setOpenBaseIcon(openBaseIcon);
        mResultView.setOpenCompareIcon(openCompareIcon);
        mResultView.setExitBaseIcon(openCompareIcon);
        mResultView.setExitCompareIcon(openBaseIcon);

        double startValue = ResultUtils.getOpenOperaionBaseValue(mResultOperation);
        double finishFirstOperationValue = ResultUtils.getFinishFirstOperationValue(mResultOperation);
        double finishOperationValue = ResultUtils.getFinishValue(mResultOperation);

        mResultView.setOpenOperaionBaseValue(startValue);
        mResultView.setOpenOperaionCompareValue(finishFirstOperationValue);
        mResultView.setExitOperaionBaseValue(finishFirstOperationValue);
        mResultView.setExitOperaionCompareValue(finishOperationValue);

        String nameBaseCurrency = Rate.getCurrencyName(mResultView.getContext(), mResultOperation.getUserHistory().getRate().getCurrencyPair().getBaseCurrency());
        String nameCompareCurrency = Rate.getCurrencyName(mResultView.getContext(), mResultOperation.getUserHistory().getRate().getCurrencyPair().getCompareCurrency());

        mResultView.setOpenOperationBaseCurrencyName(nameBaseCurrency);
        mResultView.setOpenOperationCompareCurrencyName(nameCompareCurrency);
        mResultView.setExitOperationBaseCurrencyName(nameCompareCurrency);
        mResultView.setExitOperationCompareCurrencyName(nameBaseCurrency);

        Date openOperation = mResultOperation.getUserHistory().getRate().getChangeRate();
        Date exitOperation = mResultOperation.getExitRate().getChangeRate();

        mResultView.setVisibilatyHistoryBtn(mResultOperation.isHistory() ? false : true);
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
