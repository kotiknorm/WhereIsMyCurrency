package apps.makarov.com.whereismycurrency.view.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.util.Pair;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import apps.makarov.com.whereismycurrency.R;
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

    @Bind(R.id.date)
    View dateView;
    @Bind(R.id.date_day)
    TextView dateDay;
    @Bind(R.id.date_month)
    TextView dateMonth;
    @Bind(R.id.date_year)
    TextView dateYear;
    @Bind(R.id.rate_field)
    EditText rateField;

    @Bind(R.id.base_currency_field)
    ImageView baseCurrency;
    @Bind(R.id.compare_currency_field)
    ImageView compareCurrency;

    @Bind(R.id.base_currency_name)
    TextView baseCurrencyName;
    @Bind(R.id.compare_currency_name)
    TextView compareCurrencyName;

    @Bind(R.id.enter)
    Button enterBrn;

    @Bind(R.id.base_value)
    EditText baseValue;
    @Bind(R.id.compare_value)
    EditText compareValue;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View hotView = LayoutInflater.from(getContext()).inflate(R.layout.enter_operation_fragment, container, false);

        ButterKnife.bind(this, hotView);

        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker();
            }
        });

        compareCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEnterOperationPresenter.onOpenCompareCurrencyDialog();
            }
        });

        baseCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEnterOperationPresenter.onOpenBaseCurrencyDialog();
            }
        });

        enterBrn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEnterOperationPresenter.onEnterOperation();
            }
        });

        baseValue.addTextChangedListener(textWatcherBaseValue);
        compareValue.addTextChangedListener(textWatcherCompareValue);
        rateField.addTextChangedListener(textWatcherRate);


        return hotView;
    }

    @Override
    public void showProgressDialog(int idRes) {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void setBuyCurrencyList(BaseAdapter adapter) {
        new MaterialDialog.Builder(getContext())
                .title(R.string.currency_dialog_title)
                .adapter(adapter, new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        String currency = Rate.getCodesList().get(which);
                        mEnterOperationPresenter.onEnterBaseCurrency(currency);
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    public void setSellCurrencyList(BaseAdapter adapter) {
        new MaterialDialog.Builder(getContext())
                .title(R.string.currency_dialog_title)
                .adapter(adapter, new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        String currency = Rate.getCodesList().get(which);
                        mEnterOperationPresenter.onEnterCompareCurrency(currency);
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    public void setOldRate(String rateValue) {
        rateField.setText("" + rateValue);
    }

    @Override
    public void setBaseCurrency(String currency, Drawable icon) {
        baseCurrency.setImageDrawable(icon);
        baseCurrencyName.setText(currency);
    }

    @Override
    public void setCompareCurrency(String currency, Drawable icon) {
        compareCurrency.setImageDrawable(icon);
        compareCurrencyName.setText(currency);
    }

    @Override
    public void setBaseValue(double value) {
        baseValue.setText(value + "");
    }

    @Override
    public void setCompareValue(double value) {
        compareValue.setText(value + "");
    }

    @Override
    public void setDateView(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        dateMonth.setText(c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH));
        dateDay.setText(c.get(Calendar.DAY_OF_MONTH) + "");
        dateYear.setText(c.get(Calendar.YEAR) + "");
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

    private TextWatcher textWatcherRate = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            try {
                mEnterOperationPresenter.onEnterRate(Double.parseDouble(s.toString()));
            } catch (NumberFormatException e) {

            }
        }
    };

    private TextWatcher textWatcherBaseValue = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            try {
                mEnterOperationPresenter.onEnterBaseValue(Double.parseDouble(s.toString()));
            } catch (NumberFormatException e) {

            }
        }
    };

    private TextWatcher textWatcherCompareValue = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            try {
                mEnterOperationPresenter.onEnterCompareValue(Double.parseDouble(s.toString()));
            } catch (NumberFormatException e) {

            }
        }
    };


}
