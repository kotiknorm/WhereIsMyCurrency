package apps.makarov.com.whereismycurrency.view.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import apps.makarov.com.whereismycurrency.R;
import apps.makarov.com.whereismycurrency.modules.ResultModule;
import apps.makarov.com.whereismycurrency.presenters.ResultPresenter;
import apps.makarov.com.whereismycurrency.view.base.BaseFragment;
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

    @Bind(R.id.open_operation_base_icon)
    ImageView openBaseIcon;
    @Bind(R.id.open_operation_compare_icon)
    ImageView openCompareIcon;
    @Bind(R.id.exit_operarion_base_icon)
    ImageView exitBaseIcon;
    @Bind(R.id.exit_operation_compare_icon)
    ImageView exitCompareIcon;

    @Bind(R.id.open_operation_base_value)
    TextView openOperaionBaseValue;
    @Bind(R.id.open_operation_compare_value)
    TextView openOperaionCompareValue;
    @Bind(R.id.exit_operation_base_value)
    TextView exitOperaionBaseValue;
    @Bind(R.id.exit_operation_compare_value)
    TextView exitOperaionCompareValue;

    @Bind(R.id.exit_operation_btn)
    Button exitOperaionBtn;

    @Bind(R.id.remove_operation_btn)
    Button removeOperaionBtn;

    public void setOpenOperationBaseCurrencyName(String value) {
        this.openOperationBaseCurrencyName.setText(value);
    }

    public void setOpenOperationCompareCurrencyName(String value) {
        this.openOperationCompareCurrencyName.setText(value);
    }

    public void setExitOperationBaseCurrencyName(String value) {
        this.exitOperationBaseCurrencyName.setText(value);
    }

    public void setExitOperationCompareCurrencyName(String value) {
        this.exitOperationCompareCurrencyName.setText(value);
    }

    @Override
    public void setVisibilatyHistoryBtn(boolean isShown) {
        exitOperaionBtn.setVisibility(isShown ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onAddedResultToHistory() {
        exitOperaionBtn.setVisibility(View.GONE);
    }

    @Override
    public void onRemovedResult() {
        getFragmentManager().popBackStack();
    }

    @Bind(R.id.open_operation_base_currency_name)
    TextView openOperationBaseCurrencyName;
    @Bind(R.id.open_operation_compare_currency_name)
    TextView openOperationCompareCurrencyName;
    @Bind(R.id.exit_operation_base_currency_name)
    TextView exitOperationBaseCurrencyName;
    @Bind(R.id.exit_operation_compare_currency_name)
    TextView exitOperationCompareCurrencyName;

    @Override
    public void setExitCompareIcon(Drawable drawableRes) {
        this.exitCompareIcon.setImageDrawable(drawableRes);
    }

    @Override
    public void setExitBaseIcon(Drawable drawableRes) {
        this.exitBaseIcon.setImageDrawable(drawableRes);
    }

    @Override
    public void setOpenCompareIcon(Drawable drawableRes) {
        this.openCompareIcon.setImageDrawable(drawableRes);
    }

    @Override
    public void setOpenBaseIcon(Drawable drawableRes) {
        this.openBaseIcon.setImageDrawable(drawableRes);
    }

    @Override
    public void setExitOperaionCompareValue(double exitOperaionCompareValue) {
        this.exitOperaionCompareValue.setText(exitOperaionCompareValue + "");
    }

    @Override
    public void setExitOperaionBaseValue(double exitOperaionBaseValue) {
        this.exitOperaionBaseValue.setText(exitOperaionBaseValue + "");
    }

    @Override
    public void setOpenOperaionCompareValue(double openOperaionCompareValue) {
        this.openOperaionCompareValue.setText(openOperaionCompareValue + "");
    }

    @Override
    public void setOpenOperaionBaseValue(double openOperaionBaseValue) {
        this.openOperaionBaseValue.setText(openOperaionBaseValue + "");
    }


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

        exitOperaionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResultPresenter.addResultInHistory();
            }
        });

        removeOperaionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResultPresenter.removeResult();
            }
        });

        return hotView;
    }

    @Override
    public void setDiffValue(double diff) {
        diffValueField.setText(diff + "");
    }

    @Override
    public void setColorForResult(int colorRes) {
        diffValueField.setTextColor(getResources().getColor(colorRes));

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
