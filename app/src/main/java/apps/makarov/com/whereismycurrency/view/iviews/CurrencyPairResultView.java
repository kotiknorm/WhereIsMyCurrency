package apps.makarov.com.whereismycurrency.view.iviews;

import apps.makarov.com.whereismycurrency.models.RateData;
import apps.makarov.com.whereismycurrency.view.base.BaseContextView;

/**
 * Created by makarov on 03/08/15.
 */
public interface CurrencyPairResultView extends BaseContextView {

    void setOperation(RateData rate, double value);

    void setTitle(String title);

}