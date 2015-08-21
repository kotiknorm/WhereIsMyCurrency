package apps.makarov.com.whereismycurrency.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import apps.makarov.com.whereismycurrency.R;
import apps.makarov.com.whereismycurrency.modules.BanksListModule;
import apps.makarov.com.whereismycurrency.presenters.BanksListPresenter;
import apps.makarov.com.whereismycurrency.view.base.BaseFragment;
import apps.makarov.com.whereismycurrency.view.iviews.BanksView;
import butterknife.ButterKnife;

/**
 * Created by makarov on 04/08/15.
 */
public class BanksListFragment extends BaseFragment implements BanksView {

    public static final String TAG = BanksListFragment.class.getSimpleName();

    @Inject
    BanksListPresenter mBanksListPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View hotView = LayoutInflater.from(getContext()).inflate(R.layout.list_operation_fragment, container, false);

        ButterKnife.bind(this, hotView);

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
        mBanksListPresenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mBanksListPresenter.onPause();
    }

    @Override
    public void setAdapterForRecyclerView(RecyclerView.Adapter adapter) {
//        mRecyclerView.setAdapter(adapter);
    }

    private void initializeHistoryRecyclerView() {
//        mRecyclerView.setHasFixedSize(false);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(
                new BanksListModule(this)
        );
    }


}