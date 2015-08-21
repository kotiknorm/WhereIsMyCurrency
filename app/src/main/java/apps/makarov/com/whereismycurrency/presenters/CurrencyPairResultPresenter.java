package apps.makarov.com.whereismycurrency.presenters;

import apps.makarov.com.whereismycurrency.models.RateData;
import apps.makarov.com.whereismycurrency.view.iviews.CurrencyPairResultView;

/**
 * Created by makarov on 03/08/15.
 */
public interface CurrencyPairResultPresenter {

    void onResume();

    void onPause();

    void setRate(RateData rate);

    void setValue(double v);

    void attachedView(CurrencyPairResultView view);

}
