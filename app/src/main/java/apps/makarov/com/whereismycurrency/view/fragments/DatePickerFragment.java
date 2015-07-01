package apps.makarov.com.whereismycurrency.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appeaser.sublimepickerlibrary.SublimePicker;
import com.appeaser.sublimepickerlibrary.helpers.SublimeListenerAdapter;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;

import apps.makarov.com.whereismycurrency.R;

/**
 * Created by makarov on 01/07/15.
 */

public class DatePickerFragment extends DialogFragment {

    public static final String OPTIONS_EXTRA = "OPTIONS_EXTRA";
    public static final String DATE_PICKER_TAG = "DATE_PICKER_TAG";

    private SublimePicker mSublimePicker;
    private Callback mCallback;

    public DatePickerFragment() {
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mSublimePicker = (SublimePicker) getActivity().getLayoutInflater().inflate(R.layout.sublime_picker, container);

        Bundle arguments = getArguments();
        SublimeOptions options = null;

        if (arguments != null) {
            options = arguments.getParcelable(OPTIONS_EXTRA);
        }

        mSublimePicker.initializePicker(options, mListener);
        return mSublimePicker;
    }

    public interface Callback {
        void onCancelled();

        void onDateTimeRecurrenceSet(int year, int monthOfYear, int dayOfMonth);
    }

    SublimeListenerAdapter mListener = new SublimeListenerAdapter() {
        @Override
        public void onCancelled() {
            if (mCallback != null) {
                mCallback.onCancelled();
            }
            dismiss();
        }

        @Override
        public void onDateTimeRecurrenceSet(SublimePicker sublimePicker, int year, int monthOfYear, int dayOfMonth,
                                            int hourOfDay, int minute, SublimeRecurrencePicker.RecurrenceOption recurrenceOption,
                                            String recurrenceRule) {
            if (mCallback != null) {
                mCallback.onDateTimeRecurrenceSet(year, monthOfYear, dayOfMonth);
            }
            dismiss();
        }
    };
}