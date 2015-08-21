package apps.makarov.com.whereismycurrency.presenters;

import apps.makarov.com.whereismycurrency.models.RateData;
import apps.makarov.com.whereismycurrency.view.iviews.CurrencyPairResultView;

/**
 * Created by makarov on 03/08/15.
 */
public class CurrencyPairResultPresenterImpl implements CurrencyPairResultPresenter {

    private CurrencyPairResultView mResultView;
    private double mValueCurrency;

    private RateData mRate;
    private String mTitle;

    public CurrencyPairResultPresenterImpl(RateData rate, double valueCurrency, String title) {
        setRate(rate);
        setValue(valueCurrency);
        setTitle(title);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void setRate(RateData rate) {
        mRate = rate;
    }

    @Override
    public void setValue(double v) {
        mValueCurrency = v;
    }

    @Override
    public void attachedView(CurrencyPairResultView view) {
        mResultView = view;

        if(mRate == null || mValueCurrency == 0)
            return;

        mResultView.setOperation(mRate, mValueCurrency);
        mResultView.setTitle(mTitle);
    }

    private void setTitle(String title) {
        mTitle = title;
    }
}
