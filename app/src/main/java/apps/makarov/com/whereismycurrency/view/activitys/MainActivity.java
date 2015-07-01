package apps.makarov.com.whereismycurrency.view.activitys;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import apps.makarov.com.whereismycurrency.R;
import apps.makarov.com.whereismycurrency.view.fragments.RateFragment;
import apps.makarov.com.whereismycurrency.view.fragments.ResultFragment;
import apps.makarov.com.whereismycurrency.view.iviews.MainView;


public class MainActivity extends ActionBarActivity implements MainView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showRateFragment(null);
    }

    @Override
    public void showRateFragment(Bundle bundle) {
        Fragment newFragment = new RateFragment();
        newFragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, newFragment).commit();
    }

    @Override
    public void showResultFragment(Bundle bundle) {
        Fragment newFragment = new ResultFragment();
        newFragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, newFragment).commit();
    }
}
