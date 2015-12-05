package apps.makarov.com.whereismycurrency.presenters;

import java.util.Date;

import apps.makarov.com.whereismycurrency.DateUtils;
import apps.makarov.com.whereismycurrency.R;
import apps.makarov.com.whereismycurrency.ResultUtils;
import apps.makarov.com.whereismycurrency.models.CurrencyPair;
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

    private final String OPEN_TITLE_TEXT = "Открытие операции";
    private final String EXIT_TITLE_TEXT = "Закрытие операции";

    private ResultOperation mHistory;

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
        mHistory = mWimcService.getResultOperation(key);

        double value = mHistory.getUserHistory().getValue();
        Rate historyRate = mHistory.getUserHistory().getRate();
        Rate rate = mHistory.getExitRate();

        CurrencyPair pair = rate.getCurrencyPair();
        mResultView.setVisibleBanksBtn(mWimcService.getSizeAllRatesByCurrencyPair(pair) > 0 ? true : false);

        double diff = ResultUtils.getProfitClosingOperation(value, historyRate, rate);
        mResultView.setDiffValue(diff > 0 ?
                ("+" + ResultUtils.getStrRoundSignificant(diff)) : ResultUtils.getStrRoundSignificant(diff));

        if(diff > 0){
            mResultView.setResultColor(R.color.positive_color);
        }else{
            mResultView.setResultColor(R.color.negative_color);
        }

        mResultView.setVisibleHistoryBtn(mHistory.isHistory() ? false : true);
        mResultView.setVisibleHistoryTextDate(mHistory.isHistory() ? false : true);

        CurrencyPairResultPresenter openPresenter = new CurrencyPairResultPresenterImpl
                (historyRate, value, OPEN_TITLE_TEXT);
        CurrencyPairResultPresenter exitPresenter = new CurrencyPairResultPresenterImpl
                (rate, ResultUtils.getFinishResultOperation(value, historyRate), EXIT_TITLE_TEXT);

        mResultView.addOpenOperationResult(openPresenter);
        mResultView.addExitOperationResult(exitPresenter);

        Date exitDate = rate.getChangeRate();
        String exitDateStr = DateUtils.getDateStr(exitDate);

        mResultView.setExitDate(exitDateStr);
    }

    @Override
    public void addResultInHistory() {
        mHistory = mWimcService.addResultInHistory(mHistory);
        mResultView.onAddedResultToHistory();
    }

    @Override
    public void removeResult() {
        mWimcService.removeResult(mHistory);
        mResultView.onRemovedResult();
    }

    @Override
    public void openBanksList(){
        mResultView.showBanksList(mHistory.getKey());
    }
}
