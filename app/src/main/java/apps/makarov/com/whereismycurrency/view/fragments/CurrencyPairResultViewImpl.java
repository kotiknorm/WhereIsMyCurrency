package apps.makarov.com.whereismycurrency.view.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Date;

import apps.makarov.com.whereismycurrency.DateUtils;
import apps.makarov.com.whereismycurrency.R;
import apps.makarov.com.whereismycurrency.ResultUtils;
import apps.makarov.com.whereismycurrency.models.RateData;
import apps.makarov.com.whereismycurrency.presenters.CurrencyPairResultPresenter;
import apps.makarov.com.whereismycurrency.view.iviews.CurrencyPairResultView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by makarov on 03/08/15.
 */
public class CurrencyPairResultViewImpl extends RelativeLayout implements CurrencyPairResultView {

    private final CurrencyPairResultPresenter mPairResultPresenter;

    @Bind(R.id.open_operation_base_icon)
    ImageView baseIcon;
    @Bind(R.id.open_operation_compare_icon)
    ImageView compareIcon;

    @Bind(R.id.open_operation_base_value)
    TextView baseValue;
    @Bind(R.id.open_operation_compare_value)
    TextView compareValue;

    @Bind(R.id.open_rate)
    TextView rateField;

    @Bind(R.id.title)
    TextView titleField;

    @Bind(R.id.open_operation_base_currency_name)
    TextView operationBaseCurrencyName;
    @Bind(R.id.open_operation_compare_currency_name)
    TextView operationCompareCurrencyName;

    public CurrencyPairResultViewImpl(Context context, CurrencyPairResultPresenter pairResultPresenter) {
        super(context);
        mPairResultPresenter = pairResultPresenter;
        initComponent();
    }

    private void initComponent() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.currency_pair_result_view, this);

        ButterKnife.bind(this, getRootView());

        mPairResultPresenter.attachedView(this);
    }

    private void setCompareIcon(Drawable drawableRes) {
        compareIcon.setImageDrawable(drawableRes);
    }

    private void setBaseIcon(Drawable drawableRes) {
        baseIcon.setImageDrawable(drawableRes);
    }

    private void setOperationCompareValue(double operaionCompareValue) {
        compareValue.setText(operaionCompareValue + "");
    }

    public void setOperationBaseValue(double operaionBaseValue) {
        baseValue.setText(operaionBaseValue + "");
    }

    private void setOperationCompareCurrencyName(String value) {
        operationCompareCurrencyName.setText(value);
    }

    private void setOperationBaseCurrencyName(String value) {
        operationBaseCurrencyName.setText(value);
    }

    private void setRate(String value) {
        rateField.setText(value);

    }

    private void setDate(String value) {
    }

    @Override
    public void setTitle(String titleStr) {
        titleField.setText(titleStr);
    }

    @Override
    public void setOperation(RateData rate, double valueCurrency) {

        Drawable openBaseIcon = RateData.getCurrencyIcon(getContext(), rate.getCurrencyPair().getBaseCurrency());
        Drawable openCompareIcon = RateData.getCurrencyIcon(getContext(), rate.getCurrencyPair().getCompareCurrency());

        setBaseIcon(openBaseIcon);
        setCompareIcon(openCompareIcon);

        double startValue = ResultUtils.getStartOperationValue(valueCurrency);
        double finishValue = ResultUtils.getFinishOperationValue(rate, valueCurrency);

        setOperationBaseValue(startValue);
        setOperationCompareValue(finishValue);

        String nameBaseCurrency = RateData.getCurrencyName(getContext(), rate.getCurrencyPair().getBaseCurrency());
        String nameCompareCurrency = RateData.getCurrencyName(getContext(), rate.getCurrencyPair().getCompareCurrency());

        setOperationBaseCurrencyName(nameBaseCurrency);
        setOperationCompareCurrencyName(nameCompareCurrency);

        Date openOperation = rate.getChangeRate();
        setDate(DateUtils.getDateStr(openOperation));

        setRate(rate.getValue() + "");
    }
}
