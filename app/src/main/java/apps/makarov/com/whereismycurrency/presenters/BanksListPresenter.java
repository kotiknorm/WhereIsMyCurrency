package apps.makarov.com.whereismycurrency.presenters;

/**
 * Created by makarov on 04/08/15.
 */
public interface BanksListPresenter {

    void onResume(String key);

    void onPause();

    void onRefresh(String key);

    void setRate(String resultKey);

}
