package apps.makarov.com.whereismycurrency.view.fragments;

import android.content.Context;
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
import apps.makarov.com.whereismycurrency.modules.ListOperationModule;
import apps.makarov.com.whereismycurrency.presenters.ListOperationPresenter;
import apps.makarov.com.whereismycurrency.view.base.BaseContextView;
import apps.makarov.com.whereismycurrency.view.base.BaseFragment;
import apps.makarov.com.whereismycurrency.view.iviews.ListOperationView;
import apps.makarov.com.whereismycurrency.view.iviews.MainView;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by makarov on 03/07/15.
 */
public class ListOperationFragment extends BaseFragment implements ListOperationView, BaseContextView {

    public static final String TAG = ListOperationFragment.class.getSimpleName();

    @Inject
    ListOperationPresenter mListOperationPresenter;

    @InjectView(R.id.history_list)
    RecyclerView mRecyclerView;;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View hotView = LayoutInflater.from(getContext()).inflate(R.layout.list_operation_fragment, container, false);

        ButterKnife.inject(this, hotView);
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
        mListOperationPresenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mListOperationPresenter.onPause();
    }

    @Override
    public void showResultOperation(String resultKey) {
        Bundle bundle = new Bundle();
        bundle.putString(ResultFragment.RESULT_KEY_EXTRA, resultKey);
        ((MainView) getActivity()).showResultFragment(bundle);
    }

    @Override
    public void setAdapterForRecyclerView(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void startAddOperation() {
        ((MainView) getActivity()).showEnterOperationFragment(null);
    }

    @Override
    public Context getContext() {
        return getActivity();
    }


    private void initializeHistoryRecyclerView() {
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(
                new ListOperationModule(this)
        );
    }
}
