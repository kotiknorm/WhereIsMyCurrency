package apps.makarov.com.whereismycurrency.view.iviews;

import android.graphics.drawable.Drawable;
import android.widget.BaseAdapter;

import apps.makarov.com.whereismycurrency.view.base.BaseContextView;

/**
 * Created by makarov on 26/06/15.
 */

public interface EnterOperationView extends BaseContextView {

    void showProgressDialog(int idRes);

    void hideProgressDialog();

    void setCurrencyPairList(BaseAdapter adapter);

    void setOldRate(String rateValue);

    void setBaseCurrency(String currency, Drawable icon);

    void setCompareCurrency(String currency, Drawable icon);

    void setValue(String value);

    void setDateView(String date);

    void addOperation(String resultKey);

}
