package apps.makarov.com.whereismycurrency.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import apps.makarov.com.whereismycurrency.R;
import apps.makarov.com.whereismycurrency.modules.ResultModule;
import apps.makarov.com.whereismycurrency.presenters.CurrencyPairResultPresenter;
import apps.makarov.com.whereismycurrency.presenters.ResultPresenter;
import apps.makarov.com.whereismycurrency.view.base.BaseFragment;
import apps.makarov.com.whereismycurrency.view.iviews.MainView;
import apps.makarov.com.whereismycurrency.view.iviews.ResultView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by makarov on 01/07/15.
 */

public class ResultFragment extends BaseFragment implements ResultView {

    public static final String RESULT_KEY_EXTRA = "result";

    @Inject
    ResultPresenter mResultPresenter;

    @Bind(R.id.diff_operation)
    TextView diffValueField;
    @Bind(R.id.exit_date)
    TextView exitDateField;
    @Bind(R.id.exit_operation_btn)
    Button exitOperationBtn;
    @Bind(R.id.remove_operation_btn)
    Button removeOperationBtn;
    @Bind(R.id.open_banks_list_btn)
    Button openBanksListBtn;

    @Bind(R.id.container)
    LinearLayout framesContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View hotView = LayoutInflater.from(getActivity()).inflate(R.layout.result_fragment, container, false);
        ButterKnife.bind(this, hotView);

        String key = getResultKeyExtra(getArguments());
        mResultPresenter.setUniqueOperation(key);

        exitOperationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResultPresenter.addResultInHistory();
            }
        });

        removeOperationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResultPresenter.removeResult();
            }
        });

        openBanksListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResultPresenter.openBanksList();
            }
        });

        return hotView;
    }

    @Override
    public void setExitDate(String textData){
        exitDateField.setText("Закрытие операции на " + textData);
    }

    @Override
    public void setDiffValue(String diff) {
        diffValueField.setText(diff);
    }

    @Override
    public void setResultColor(int colorRes) {
        diffValueField.setTextColor(getResources().getColor(colorRes));
    }

    @Override
    public void setVisibleHistoryTextDate(boolean isShown){
        exitDateField.setVisibility(isShown ? View.GONE : View.VISIBLE);
    }

    @Override
    public void setVisibleHistoryBtn(boolean isShown) {
        exitOperationBtn.setVisibility(isShown ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setVisibleBanksBtn(boolean isShown) {
        openBanksListBtn.setVisibility(isShown ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onAddedResultToHistory() {
        exitOperationBtn.setVisibility(View.GONE);
        exitDateField.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRemovedResult() {
        getFragmentManager().popBackStack();
    }

    @Override
    public void showBanksList(String key) {
        Bundle bundle = new Bundle();
        bundle.putString(ResultFragment.RESULT_KEY_EXTRA, key);
        ((MainView) getActivity()).showListBanksFragment(bundle);
    }

    @Override
    public void addOpenOperationResult(CurrencyPairResultPresenter presenter) {
        View openRate = new CurrencyPairResultViewImpl(getContext(), presenter);
        framesContainer.addView(openRate, 0);
    }

    @Override
    public void addExitOperationResult(CurrencyPairResultPresenter presenter) {
        View exitRate = new CurrencyPairResultViewImpl(getContext(), presenter);
        framesContainer.addView(exitRate, 1);
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
