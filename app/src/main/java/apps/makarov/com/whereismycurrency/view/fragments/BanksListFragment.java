package apps.makarov.com.whereismycurrency.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import apps.makarov.com.whereismycurrency.R;
import apps.makarov.com.whereismycurrency.models.Rate;
import apps.makarov.com.whereismycurrency.modules.BanksListModule;
import apps.makarov.com.whereismycurrency.presenters.BanksListPresenter;
import apps.makarov.com.whereismycurrency.view.adapters.BankAdapter;
import apps.makarov.com.whereismycurrency.view.base.BaseFragment;
import apps.makarov.com.whereismycurrency.view.iviews.BanksView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by makarov on 04/08/15.
 */
public class BanksListFragment extends BaseFragment implements BanksView {

    public static final String TAG = BanksListFragment.class.getSimpleName();

    @Inject
    BanksListPresenter mBanksListPresenter;

    @Bind(R.id.banks_list)
    RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View hotView = LayoutInflater.from(getContext()).inflate(R.layout.list_banks_fragment, container, false);

        ButterKnife.bind(this, hotView);

        //String key = getResultKeyExtra(getArguments());
        //mBanksListPresenter.setRate(key);

        return hotView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeHistoryRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
        String key = getResultKeyExtra(getArguments());
        mBanksListPresenter.onResume(key);
    }

    @Override
    public void onPause() {
        super.onPause();
        mBanksListPresenter.onPause();
    }

    @Override
    public void setAdapterForRecyclerView(List<Rate> banks) {
        BankAdapter bankAdapter = new BankAdapter(banks);
        mRecyclerView.setAdapter(bankAdapter);
    }

    @Override
    public void setVisabileSplash(boolean isShown) {

    }

    private void initializeHistoryRecyclerView() {
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(
                new BanksListModule(this)
        );
    }

    private String getResultKeyExtra(Bundle savedInstanceState){
        if(savedInstanceState==null)
            return null;
        if(!savedInstanceState.containsKey(ResultFragment.RESULT_KEY_EXTRA))
            return null;
        return savedInstanceState.getString(ResultFragment.RESULT_KEY_EXTRA);

    }

}