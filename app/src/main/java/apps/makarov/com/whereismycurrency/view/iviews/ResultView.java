package apps.makarov.com.whereismycurrency.view.iviews;

import apps.makarov.com.whereismycurrency.presenters.CurrencyPairResultPresenter;
import apps.makarov.com.whereismycurrency.view.base.BaseContextView;

/**
 * Created by makarov on 01/07/15.
 */
public interface ResultView extends BaseContextView {

    void setExitDate(String textData);

    void setDiffValue(String diff);

    void setResultColor(int colorRes);

    void setVisibleHistoryTextDate(boolean isShown);

    void setVisibleHistoryBtn(boolean isShown);

    void onAddedResultToHistory();

    void onRemovedResult();

    void showBanksList(String key);

    void addOpenOperationResult(CurrencyPairResultPresenter pairResultPresenter);

    void addExitOperationResult(CurrencyPairResultPresenter pairResultPresenter);

}
