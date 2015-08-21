package apps.makarov.com.whereismycurrency.view.iviews;

import apps.makarov.com.whereismycurrency.presenters.CurrencyPairResultPresenter;
import apps.makarov.com.whereismycurrency.view.base.BaseContextView;

/**
 * Created by makarov on 01/07/15.
 */
public interface ResultView extends BaseContextView {

    void setDiffValue(String diff);

    void setResultColor(int colorRes);

    void setVisibileHistoryBtn(boolean isShown);

    void onAddedResultToHistory();

    void onRemovedResult();

    void addOpenOperationResult(CurrencyPairResultPresenter pairResultPresenter);

    void addExitOperationResult(CurrencyPairResultPresenter pairResultPresenter);

}
