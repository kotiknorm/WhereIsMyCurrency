package apps.makarov.com.whereismycurrency.view.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;

import apps.makarov.com.whereismycurrency.R;
import apps.makarov.com.whereismycurrency.models.Rate;
import apps.makarov.com.whereismycurrency.models.ResultOperation;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by makarov on 30/06/15.
 */
public class ResultOperationViewHolder extends RecyclerView.ViewHolder {
    public static final String TAG = ResultOperationViewHolder.class.getSimpleName();

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

    public ResultOperationViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindStoryToView(ResultOperation history) {
        baseValueField.setText(getBaseValueString(history));
        baseNameCurrencyField.setText(Rate.getCurrencyName(itemView.getContext(), history.getUserHistory().getRate().getCurrencyPair().getBaseCurrency()));
        baseCurrencyImage.setImageDrawable(Rate.getCurrencyIcon(itemView.getContext(), history.getUserHistory().getRate().getCurrencyPair().getBaseCurrency()));
        compareCurrencyImage.setImageDrawable(Rate.getCurrencyIcon(itemView.getContext(), history.getUserHistory().getRate().getCurrencyPair().getCompareCurrency()));

        diffBaseCurrencyValue.setText(getDiffStr(history));
        if(getDiff(history) > 0){
            diffBaseCurrencyValue.setTextColor(itemView.getContext().getResources().getColor(R.color.positive_color));
        }else{
            diffBaseCurrencyValue.setTextColor(itemView.getContext().getResources().getColor(R.color.negative_color));
        }

        balanceField.setText(getFinishValueStr(history));
    }


    private String getBaseValueString(ResultOperation operation){
        return itemView.getContext().getString(R.string.history_item_subtitle,
                operation.getUserHistory().getValue(),
                operation.getUserHistory().getRate().getCurrencyPair().getBaseCurrency());
    }

    private double getDiff(ResultOperation operation){

        double startValue = operation.getUserHistory().getValue();
        double finishValue = getFinishValue(operation);

        return BigDecimal.valueOf(finishValue - startValue).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    private String getDiffStr(ResultOperation operation){

        double diff = getDiff(operation);

        return diff > 0 ? "+" + diff : diff + "";
    }

    private double getFinishValue(ResultOperation operation){
        return operation.getUserHistory().getValue() * operation.getUserHistory().getRate().getValue() * operation.getExitRate().getValue();
    }

    private String getFinishValueStr(ResultOperation operation){
        String str = BigDecimal.valueOf(getFinishValue(operation)).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue() + "";
        return itemView.getContext().getString(R.string.history_balance, str);
    }
}
