package apps.makarov.com.whereismycurrency.view.fragments;

import android.os.Bundle;

import javax.inject.Inject;

import apps.makarov.com.whereismycurrency.presenters.ResultPresenter;
import apps.makarov.com.whereismycurrency.view.base.BaseFragment;
import apps.makarov.com.whereismycurrency.view.iviews.ResultView;

/**
 * Created by makarov on 01/07/15.
 */

public class ResultFragment extends BaseFragment implements ResultView {

    @Inject
    ResultPresenter mResultPresenter;


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


}
