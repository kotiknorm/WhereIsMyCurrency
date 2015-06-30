package apps.makarov.com.whereismycurrency.view.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import apps.makarov.com.whereismycurrency.DateUtils;
import apps.makarov.com.whereismycurrency.R;
import apps.makarov.com.whereismycurrency.models.UserHistory;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by makarov on 30/06/15.
 */
public class UserHistoryViewHolder extends RecyclerView.ViewHolder {
    public static final String TAG = UserHistoryViewHolder.class.getSimpleName();

    @InjectView(R.id.rate)
    TextView rateTextView;
    @InjectView(R.id.value)
    TextView valueTextView;
    @InjectView(R.id.date)
    TextView dateTextView;

    public UserHistoryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);
    }

    public void bindStoryToView(UserHistory history) {
        rateTextView.setText(getRateString(history));
        valueTextView.setText(getCurrencyOperationString(history));
        dateTextView.setText(getDate(history));
    }

    private String getDate(UserHistory history){
        SimpleDateFormat df = DateUtils.getWimcFormat();
        return df.format(history.getDate());
    }

    private String getCurrencyOperationString(UserHistory history){
        return itemView.getContext().getString(R.string.history_item_title,
                history.getValue(), history.getRate().getBaseCurrency());
    }

    private String getRateString(UserHistory history){
        return itemView.getContext().getString(R.string.history_item_subtitle,
                history.getRate().getValue(), history.getRate().getCompareCurrency());
    }
}
