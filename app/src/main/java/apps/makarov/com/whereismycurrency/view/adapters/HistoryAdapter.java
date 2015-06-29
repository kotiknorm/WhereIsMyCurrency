package apps.makarov.com.whereismycurrency.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import apps.makarov.com.whereismycurrency.R;
import apps.makarov.com.whereismycurrency.models.UserHistory;
import apps.makarov.com.whereismycurrency.view.adapters.viewholders.UserHistoryViewHolder;

/**
 * Created by makarov on 30/06/15.
 */
public class HistoryAdapter extends RecyclerView.Adapter<UserHistoryViewHolder> {
    public static final String TAG = HistoryAdapter.class.getSimpleName();

    private Context mContext;
    private List<UserHistory> mHistoryItems;

    public HistoryAdapter(Context context, List<UserHistory>stories) {
        this.mContext = context;
        this.mHistoryItems = stories;
    }

    @Override
    public UserHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserHistoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_history_item, parent, false));
    }

    @Override
    public void onBindViewHolder(UserHistoryViewHolder holder, int position) {
        holder.bindStoryToView(mContext, mHistoryItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mHistoryItems.size();
    }


}
