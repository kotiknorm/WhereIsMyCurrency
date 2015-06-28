package apps.makarov.com.whereismycurrency.presenters;

/**
 * Created by makarov on 26/06/15.
 */

public interface RatePresenter {

    void onResume();

    void onPause();

    void onDestroy();

    void onRefresh();
}
