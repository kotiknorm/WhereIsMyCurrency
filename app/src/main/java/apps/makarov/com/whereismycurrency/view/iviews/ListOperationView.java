package apps.makarov.com.whereismycurrency.view.iviews;

import java.util.List;

import apps.makarov.com.whereismycurrency.models.ResultOperationData;
import apps.makarov.com.whereismycurrency.view.base.BaseContextView;

/**
 * Created by makarov on 03/07/15.
 */
public interface ListOperationView extends BaseContextView {

    void showResultOperation(String key);

    void showHistoryList(List<ResultOperationData> rates);

    void startAddOperation();

    void hideProgress();

    void showProgress();

    void showErrorView(String message);

    void setVisabileSplash(boolean isShown);
}
