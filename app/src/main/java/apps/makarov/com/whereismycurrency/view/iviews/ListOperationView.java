package apps.makarov.com.whereismycurrency.view.iviews;

import android.support.v7.widget.RecyclerView;

/**
 * Created by makarov on 03/07/15.
 */
public interface ListOperationView {

    void showResultOperation(String resultKey);

    void setAdapterForRecyclerView(RecyclerView.Adapter adapter);

    void startAddOperation();
}
