package apps.makarov.com.whereismycurrency.presenters;

import java.util.Date;

/**
 * Created by makarov on 26/06/15.
 */

public interface RatePresenter {

    void onResume();

    void onPause();

    void onDestroy();

    void onRefresh();

    void onEnterOperation(String baseCurrency, String compareCurrency, double value, double rate);

    void onEnterDateOperation(String baseCurrency, String compareCurrency, Date date);
}
