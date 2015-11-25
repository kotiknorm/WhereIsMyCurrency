package apps.makarov.com.whereismycurrency.presenters;

/**
 * Created by makarov on 01/07/15.
 */
public interface ResultPresenter {

    void onResume();

    void onPause();

    void onDestroy();

    void onRefresh();

    void setUniqueOperation(String key);

    void addResultInHistory();

    void removeResult();

    void openListBanks();
}
