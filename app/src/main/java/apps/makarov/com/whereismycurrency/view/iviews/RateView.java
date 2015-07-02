package apps.makarov.com.whereismycurrency.view.iviews;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.widget.BaseAdapter;

import apps.makarov.com.whereismycurrency.view.base.BaseContextView;

/**
 * Created by makarov on 26/06/15.
 */

public interface RateView extends BaseContextView {

    void showProgressDialog(int idRes);

    void hideProgressDialog();

    void showResultOperation(String resultKey);

    void setAdapterForRecyclerView(RecyclerView.Adapter adapter);

    void setCurrencyPairList(BaseAdapter adapter);

    void setOldRate(String rateValue);

    void setBaseCurrency(String currency, Drawable icon);

    void setCompareCurrency(String currency, Drawable icon);

}
