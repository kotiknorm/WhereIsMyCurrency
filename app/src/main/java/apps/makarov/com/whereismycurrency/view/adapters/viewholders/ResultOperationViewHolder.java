package apps.makarov.com.whereismycurrency.view.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import apps.makarov.com.whereismycurrency.DateUtils;
import apps.makarov.com.whereismycurrency.R;
import apps.makarov.com.whereismycurrency.models.ResultOperation;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by makarov on 30/06/15.
 */
public class ResultOperationViewHolder extends RecyclerView.ViewHolder {
    public static final String TAG = ResultOperationViewHolder.class.getSimpleName();

    @InjectView(R.id.rate)
    TextView rateTextView;
    @InjectView(R.id.value)
    TextView valueTextView;
    @InjectView(R.id.date)
    TextView dateTextView;

    public ResultOperationViewHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);
    }

    public void bindStoryToView(ResultOperation history) {
        rateTextView.setText(getRateString(history));
        valueTextView.setText(getCurrencyOperationString(history));
        dateTextView.setText(getDate(history));
    }

    private String getDate(ResultOperation history){
        SimpleDateFormat df = DateUtils.getWimcFormat();
        return df.format(history.getDate());
    }

    private String getCurrencyOperationString(ResultOperation operation){
        return itemView.getContext().getString(R.string.history_item_title,
                operation.getUserHistory().getValue(), operation.getUserHistory().getRate().getCurrencyPair().getBaseCurrency());
    }

    private String getRateString(ResultOperation operation){
        return itemView.getContext().getString(R.string.history_item_subtitle,
                operation.getUserHistory().getRate().getValue(), operation.getUserHistory().getRate().getCurrencyPair().getCompareCurrency());
    }
}
