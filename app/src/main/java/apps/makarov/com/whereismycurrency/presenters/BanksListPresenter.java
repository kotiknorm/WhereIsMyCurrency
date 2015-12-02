package apps.makarov.com.whereismycurrency.presenters;

import apps.makarov.com.whereismycurrency.models.ResultOperation;

/**
 * Created by makarov on 04/08/15.
 */
public interface BanksListPresenter {

    void onResume();

    void onPause();

    void onRefresh();

    void setRate(String resultKey);

    ResultOperation getHistory();

}
