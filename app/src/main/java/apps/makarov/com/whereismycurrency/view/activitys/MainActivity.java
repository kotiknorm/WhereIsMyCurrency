package apps.makarov.com.whereismycurrency.view.activitys;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import apps.makarov.com.whereismycurrency.R;
import apps.makarov.com.whereismycurrency.view.fragments.EnterOperationFragment;
import apps.makarov.com.whereismycurrency.view.fragments.ListOperationFragment;
import apps.makarov.com.whereismycurrency.view.fragments.ResultFragment;
import apps.makarov.com.whereismycurrency.view.iviews.MainView;


public class MainActivity extends AppCompatActivity implements MainView {

    private static final String DEFAULT_FRAGMENT_TAG = "DEFAULT_FRAGMENT_TAG";
    private static final String DEFAULT_FRAGMENT_STACK_NAME = "DEFAULT_FRAGMENT_STACK_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(DEFAULT_FRAGMENT_TAG);
        if (fragment == null) {
            setBaseFragment(new ListOperationFragment());
        }
    }

    @Override
    public void showEnterOperationFragment(Bundle bundle) {
        Fragment newFragment = new EnterOperationFragment();
        newFragment.setArguments(bundle);
        setFragment(newFragment, true);
    }

    @Override
    public void showResultFragment(Bundle bundle) {
        Fragment newFragment = new ResultFragment();
        newFragment.setArguments(bundle);
        setFragment(newFragment, true);
    }

    @Override
    public void showListOperationFragment(Bundle bundle) {
        Fragment newFragment = new ListOperationFragment();
        newFragment.setArguments(bundle);
        setFragment(newFragment, true);
    }

    private void setFragment(Fragment fragment, boolean saveInBackstack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment, DEFAULT_FRAGMENT_TAG);
        if(saveInBackstack)
            ft.addToBackStack(DEFAULT_FRAGMENT_STACK_NAME);
        ft.commit();
    }

    private void setBaseFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.container, fragment, DEFAULT_FRAGMENT_TAG)
                .commit();
    }

}
