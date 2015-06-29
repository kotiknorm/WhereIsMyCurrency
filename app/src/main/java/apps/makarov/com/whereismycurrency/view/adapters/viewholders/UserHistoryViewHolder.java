package apps.makarov.com.whereismycurrency.view.adapters.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

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

    public void bindStoryToView(Context context, UserHistory story) {
        rateTextView.setText("" + story.getRate().getValue());
        valueTextView.setText("" + story.getValue());
        dateTextView.setText(story.getDate().toString());
    }
}
