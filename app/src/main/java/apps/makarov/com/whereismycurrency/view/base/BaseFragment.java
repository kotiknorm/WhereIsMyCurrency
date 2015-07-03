package apps.makarov.com.whereismycurrency.view.base;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.Arrays;
import java.util.List;

import apps.makarov.com.whereismycurrency.WimcApplication;
import dagger.ObjectGraph;

/**
 * Created by makarov on 26/06/15.
 */

public abstract class BaseFragment extends Fragment implements BaseContextView {
    public static final String TAG = BaseFragment.class.getSimpleName();

    private ObjectGraph mObjectGraph;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mObjectGraph = WimcApplication.getApplication(getActivity()).buildScopedObjectGraph(getModules().toArray());
        mObjectGraph.inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mObjectGraph = null;
    }

    @Override
    public Context getContext(){
        return getActivity();
    }

    protected List<Object> getModules() {
        return Arrays.<Object>asList(
                // Empty.
        );
    }
}