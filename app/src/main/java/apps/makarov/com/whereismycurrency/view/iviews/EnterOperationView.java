package apps.makarov.com.whereismycurrency.view.iviews;

import android.graphics.drawable.Drawable;
import android.widget.BaseAdapter;

import java.util.Date;

import apps.makarov.com.whereismycurrency.view.base.BaseContextView;

/**
 * Created by makarov on 26/06/15.
 */

public interface EnterOperationView extends BaseContextView {

    void showProgressDialog(int idRes);

    void hideProgressDialog();

    void setBuyCurrencyList(BaseAdapter adapter);

    void setSellCurrencyList(BaseAdapter adapter);

    void setOldRate(String rateValue);

    void setBaseCurrency(String currency, Drawable icon);

    void setCompareCurrency(String currency, Drawable icon);

    void setBaseValue(double value);

    void setCompareValue(double value);

    void setDateView(Date date);

    void addOperation(String resultKey);

    void showErrorView(String message);

}
