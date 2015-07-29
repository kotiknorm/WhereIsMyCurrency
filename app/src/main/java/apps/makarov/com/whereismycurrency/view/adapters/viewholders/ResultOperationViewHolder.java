package apps.makarov.com.whereismycurrency.view.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import apps.makarov.com.whereismycurrency.R;
import apps.makarov.com.whereismycurrency.ResultUtils;
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
    }



}
