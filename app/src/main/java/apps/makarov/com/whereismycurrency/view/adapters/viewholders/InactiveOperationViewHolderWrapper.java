package apps.makarov.com.whereismycurrency.view.adapters.viewholders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

import apps.makarov.com.whereismycurrency.DateUtils;
import apps.makarov.com.whereismycurrency.R;
import apps.makarov.com.whereismycurrency.ResultUtils;
import apps.makarov.com.whereismycurrency.models.Rate;
import apps.makarov.com.whereismycurrency.models.ResultOperation;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by makarov on 02/08/15.
 */
public class InactiveOperationViewHolderWrapper extends ViewHolderWrapper<ResultOperation> {
    public static final String TAG = ViewHolderWrapper.class.getSimpleName();

    @Bind(R.id.base_value)
    TextView baseValueField;
    @Bind(R.id.base_name_currency)
    TextView baseNameCurrencyField;
    @Bind(R.id.base_currency_image)
    ImageView baseCurrencyImage;
    @Bind(R.id.compare_currency_image)
    ImageView compareCurrencyImage;
    @Bind(R.id.diff_base_currency)
    TextView diffBaseCurrencyValue;
    @Bind(R.id.balance_field)
    TextView balanceField;
    @Bind(R.id.open_date)
    TextView openDateField;
    @Bind(R.id.exit_date)
    TextView exitDateField;


    public InactiveOperationViewHolderWrapper(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_history_item, parent, false));
        ButterKnife.bind(this, itemView);
    }

    public void bindModelToView(ResultOperation history) {

        baseValueField.setText(ResultUtils.getBaseValueString(itemView.getContext(), history));
        baseNameCurrencyField.setText(Rate.getCurrencyName(itemView.getContext(), history.getUserHistory().getRate().getCurrencyPair().getBaseCurrency()));
        baseCurrencyImage.setImageDrawable(Rate.getCurrencyIcon(itemView.getContext(), history.getUserHistory().getRate().getCurrencyPair().getBaseCurrency()));
        compareCurrencyImage.setImageDrawable(Rate.getCurrencyIcon(itemView.getContext(), history.getUserHistory().getRate().getCurrencyPair().getCompareCurrency()));

        diffBaseCurrencyValue.setText(ResultUtils.getDiffStr(history));
        if(ResultUtils.getDiff(history) > 0){
            diffBaseCurrencyValue.setTextColor(itemView.getContext().getResources().getColor(R.color.positive_color));
        }else{
            diffBaseCurrencyValue.setTextColor(itemView.getContext().getResources().getColor(R.color.negative_color));
        }

        balanceField.setText(ResultUtils.getFinishValueStr(itemView.getContext(), history));

        Date openDate = history.getUserHistory().getDate();
        Date exitDate = history.getExitRate().getChangeRate();

        String openDateStr = DateUtils.getDateStr(openDate);
        String exitDateStr = DateUtils.getDateStr(exitDate);

        openDateField.setText(openDateStr);
        exitDateField.setText(exitDateStr);
    }

    @Override
    public void setOnClicklistener(final View.OnClickListener listener) {
        itemView.setOnClickListener(listener);
    }



}
