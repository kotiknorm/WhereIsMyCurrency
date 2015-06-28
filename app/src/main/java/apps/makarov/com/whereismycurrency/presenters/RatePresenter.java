package apps.makarov.com.whereismycurrency.presenters;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by makarov on 26/06/15.
 */

public interface RatePresenter {

    void onActivityCreated(Bundle savedInstanceState);

    void onResume();

    void onPause();

    void onSaveInstanceState(Bundle outState);

    void onDestroy();

    void onCreateOptionsMenu(Menu menu, MenuInflater inflater);

    boolean onOptionsItemSelected(MenuItem item);

    void onRefresh();
}
