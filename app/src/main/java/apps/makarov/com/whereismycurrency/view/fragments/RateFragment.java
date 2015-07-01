package apps.makarov.com.whereismycurrency.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import apps.makarov.com.whereismycurrency.R;
import apps.makarov.com.whereismycurrency.models.Rate;
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

    @InjectView(R.id.result)
    TextView resultTextView;
    @InjectView(R.id.value)
    EditText valueTextView;
    @InjectView(R.id.rate)
    EditText rateTextView;
    @InjectView(R.id.enter)
    Button enterButton;
    @InjectView(R.id.history_list)
    RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View hotView = LayoutInflater.from(getActivity()).inflate(R.layout.rate_fragment, container, false);

        ButterKnife.inject(this, hotView);

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double value = Double.parseDouble(valueTextView.getEditableText().toString());
                double rate = Double.parseDouble(rateTextView.getEditableText().toString());

                mRatePresenter.enterOperation(Rate.RUB_CODE, Rate.EUR_CODE, value, rate);

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
    public void showSwipeRefreshLayout() {

    }

    @Override
    public void hideSwipeRefreshLayout() {

    }

    @Override
    public void setResultOperation(String result) {
        resultTextView.setText(result);
    }

    @Override
    public void setAdapterForRecyclerView(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setAsyncRate(int rateValue) {
        rateTextView.setText("" + rateValue);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeHistoryRecyclerView();
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRatePresenter.onDestroy();
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
    public Context getContext() {
        return this.getActivity();
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(
                new RateModule(this)
        );
    }

    private void initializeHistoryRecyclerView() {
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    DatePickerFragment.Callback mFragmentCallback = new DatePickerFragment.Callback() {
        @Override
        public void onCancelled() {
        }

        @Override
        public void onDateTimeRecurrenceSet(int year, int monthOfYear, int dayOfMonth) {
            Log.d(TAG, "onDateTimeRecurrenceSet");
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

    private void openDatePicker(){
        DatePickerFragment pickerFrag = new DatePickerFragment();
        pickerFrag.setCallback(mFragmentCallback);
        Pair<Boolean, SublimeOptions> optionsPair = getDatePickerOptions();
        Bundle bundle = new Bundle();
        bundle.putParcelable(DatePickerFragment.OPTIONS_EXTRA, optionsPair.second);
        pickerFrag.setArguments(bundle);
        pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        pickerFrag.show(getActivity().getSupportFragmentManager(), DatePickerFragment.DATE_PICKER_TAG);
    }




}
