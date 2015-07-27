package apps.makarov.com.whereismycurrency.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import apps.makarov.com.whereismycurrency.R;
import apps.makarov.com.whereismycurrency.modules.ResultModule;
import apps.makarov.com.whereismycurrency.presenters.ResultPresenter;
import apps.makarov.com.whereismycurrency.view.base.BaseFragment;
import apps.makarov.com.whereismycurrency.view.iviews.ResultView;
import butterknife.ButterKnife;

/**
 * Created by makarov on 01/07/15.
 */

public class ResultFragment extends BaseFragment implements ResultView {

    public static final String RESULT_KEY_EXTRA = "result";

    @Inject
    ResultPresenter mResultPresenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View hotView = LayoutInflater.from(getActivity()).inflate(R.layout.result_fragment, container, false);
        ButterKnife.bind(this, hotView);

        String resultKey = getResultKeyExtra(getArguments());
        mResultPresenter.setResult(resultKey);

        return hotView;
    }

    @Override
    public void showResultOperation(String resultKey) {
    }

    @Override
    public void onResume() {
        super.onResume();
        mResultPresenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mResultPresenter.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mResultPresenter.onDestroy();
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(
                new ResultModule(this)
        );
    }

    private String getResultKeyExtra(Bundle savedInstanceState){
        if(savedInstanceState==null)
            return null;
        if(!savedInstanceState.containsKey(RESULT_KEY_EXTRA))
            return null;
        return savedInstanceState.getString(RESULT_KEY_EXTRA);

    }

}
