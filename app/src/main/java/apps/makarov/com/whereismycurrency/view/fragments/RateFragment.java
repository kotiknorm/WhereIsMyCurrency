package apps.makarov.com.whereismycurrency.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import apps.makarov.com.whereismycurrency.R;
import apps.makarov.com.whereismycurrency.modules.RateModule;
import apps.makarov.com.whereismycurrency.presenters.RatePresenter;
import apps.makarov.com.whereismycurrency.view.base.BaseFragment;
import apps.makarov.com.whereismycurrency.view.iviews.RateView;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by makarov on 26/06/15.
 */

public class RateFragment extends BaseFragment implements RateView {

    public static final String TAG = RateFragment.class.getSimpleName();

    @Inject
    RatePresenter mRatePresenter;

    @InjectView(R.id.sell)
    TextView sellTextView;
    @InjectView(R.id.buy)
    TextView buyTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View hotView = LayoutInflater.from(getActivity()).inflate(R.layout.rate_fragment, container, false);

        ButterKnife.inject(this, hotView);

        return hotView;
    }

    @Override
    public void showProgressDialog(int idRes) {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void showSwipeRefreshLayout() {

    }

    @Override
    public void hideSwipeRefreshLayout() {

    }

    @Override
    public void setValue(double fistValue, double secondValue) {
        sellTextView.setText(String.valueOf(fistValue));
        buyTextView.setText(String.valueOf(secondValue));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRatePresenter.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        mRatePresenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

        mRatePresenter.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        mRatePresenter.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mRatePresenter.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        mRatePresenter.onCreateOptionsMenu(menu, inflater);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mRatePresenter.onOptionsItemSelected(item);
    }

    @Override
    public Context getContext() {
        return this.getActivity();
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(
                new RateModule(this)
        );
    }
}
