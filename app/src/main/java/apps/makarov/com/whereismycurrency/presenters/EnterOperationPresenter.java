package apps.makarov.com.whereismycurrency.presenters;

import java.util.Date;

/**
 * Created by makarov on 26/06/15.
 */

public interface EnterOperationPresenter {

    void onResume();

    void onPause();

    void onDestroy();

    void onRefresh();

    void onEnterOperation();

    void onEnterRate(double rate);

    void onEnterBaseValue(double rate);

    void onEnterCompareValue(double rate);

    void onEnterDateOperation(Date date);

    void onEnterCompareCurrency(String pair);

    void onEnterBaseCurrency(String pair);

    void updateResults(Date date);

    void onOpenCompareCurrencyDialog();

    void onOpenBaseCurrencyDialog();

    void correctingValue();

}
