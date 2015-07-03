package apps.makarov.com.whereismycurrency.presenters;

import java.util.Date;

import apps.makarov.com.whereismycurrency.models.CurrencyPair;

/**
 * Created by makarov on 26/06/15.
 */

public interface EnterOperationPresenter {

    void onResume();

    void onPause();

    void onDestroy();

    void onRefresh();

    void onEnterOperation(String baseCurrency, String compareCurrency, double value, double rate);

    void onEnterDateOperation(Date date);

    void onEnterCurrencyPair(CurrencyPair pair);

    void onProcessLoadCurrencyPairs();
}
