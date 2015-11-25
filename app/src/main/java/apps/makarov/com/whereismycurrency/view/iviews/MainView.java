package apps.makarov.com.whereismycurrency.view.iviews;

import android.os.Bundle;

/**
 * Created by makarov on 26/06/15.
 */
public interface MainView {

    void showEnterOperationFragment(Bundle bundle);

    void showResultFragment(Bundle bundle);

    void showListOperationFragment(Bundle bundle);

    void showListBanksFragment(Bundle bundle);

}
