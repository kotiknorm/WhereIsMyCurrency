package apps.makarov.com.whereismycurrency.view.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import apps.makarov.com.whereismycurrency.R;
import apps.makarov.com.whereismycurrency.models.CurrencyPair;
import apps.makarov.com.whereismycurrency.models.Rate;
import apps.makarov.com.whereismycurrency.modules.EnterOperationModule;
import apps.makarov.com.whereismycurrency.presenters.EnterOperationPresenter;
import apps.makarov.com.whereismycurrency.view.base.BaseFragment;
import apps.makarov.com.whereismycurrency.view.iviews.EnterOperationView;
import apps.makarov.com.whereismycurrency.view.iviews.MainView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by makarov on 26/06/15.
 */

public class EnterOperationFragment extends BaseFragment implements EnterOperationView {

    public static final String TAG = EnterOperationFragment.class.getSimpleName();

    @Inject
    EnterOperationPresenter mEnterOperationPresenter;

    @Bind(R.id.value)
    EditText valueTextView;
    @Bind(R.id.rate)
    EditText rateTextView;
    @Bind(R.id.enter)
    Button enterButton;
    @Bind(R.id.date)
    TextView dateTextView;
    @Bind(R.id.pair)
    TextView pairTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View hotView = LayoutInflater.from(getContext()).inflate(R.layout.enter_operation_fragment, container, false);

        ButterKnife.bind(this, hotView);

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double value = Double.parseDouble(valueTextView.getEditableText().toString());
                double rate = Double.parseDouble(rateTextView.getEditableText().toString());
                mEnterOperationPresenter.onEnterOperation(value, rate);
            }
        });

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker();
            }
        });

        pairTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCurrencyListView();
            }
        });

        return hotView;
    }

    @Override
    public void showProgressDialog(int idRes) {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void setCurrencyPairList(BaseAdapter adapter) {
        new MaterialDialog.Builder(getContext())
                .title(R.string.currency_dialog_title)
                .adapter(adapter, new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        CurrencyPair pair = Rate.getPairCodesList().get(which);
                        mEnterOperationPresenter.onEnterCurrencyPair(pair);
                        pairTextView.setText(pair.getBaseCurrency() + "_" + pair.getCompareCurrency());
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void setOldRate(String rateValue) {
        rateTextView.setText("" + rateValue);
    }

    @Override
    public void setBaseCurrency(String currency, Drawable icon) {

    }

    @Override
    public void setCompareCurrency(String currency, Drawable icon) {

    }

    @Override
    public void setValue(String value) {
        valueTextView.setText(value);
    }

    @Override
    public void setDateView(String date) {
        dateTextView.setText(date);
    }

    @Override
    public void addOperation(String resultKey) {
        Bundle bundle = new Bundle();
        bundle.putString(ResultFragment.RESULT_KEY_EXTRA, resultKey);
        ((MainView) getActivity()).showListOperationFragment(bundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mEnterOperationPresenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mEnterOperationPresenter.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mEnterOperationPresenter.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(
                new EnterOperationModule(this)
        );
    }

    DatePickerFragment.Callback mFragmentCallback = new DatePickerFragment.Callback() {
        @Override
        public void onCancelled() {
        }

        @Override
        public void onDateTimeRecurrenceSet(Date date) {
            Log.d(TAG, "date -" + date.toString());
            dateTextView.setText(date.toString());
            onEnterDate(date);
        }
    };

    private Pair<Boolean, SublimeOptions> getDatePickerOptions() {
        SublimeOptions options = new SublimeOptions();
        int displayOptions = 0;

        displayOptions |= SublimeOptions.ACTIVATE_DATE_PICKER;
        options.setPickerToShow(SublimeOptions.Picker.DATE_PICKER);
        options.setDisplayOptions(displayOptions);

        return new Pair<>(displayOptions != 0 ? Boolean.TRUE : Boolean.FALSE, options);
    }

    private void openDatePicker() {
        DatePickerFragment pickerFrag = new DatePickerFragment();
        pickerFrag.setCallback(mFragmentCallback);
        Pair<Boolean, SublimeOptions> optionsPair = getDatePickerOptions();
        Bundle bundle = new Bundle();
        bundle.putParcelable(DatePickerFragment.OPTIONS_EXTRA, optionsPair.second);
        pickerFrag.setArguments(bundle);
        pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        pickerFrag.show(getFragmentManager(), DatePickerFragment.DATE_PICKER_TAG);
    }

    private void onEnterDate(Date date) {
        mEnterOperationPresenter.onEnterDateOperation(date);
    }

    private void openCurrencyListView(){
        mEnterOperationPresenter.onProcessLoadCurrencyPairs();
    }



}
