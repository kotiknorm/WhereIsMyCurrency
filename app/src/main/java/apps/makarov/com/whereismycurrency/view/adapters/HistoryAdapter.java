package apps.makarov.com.whereismycurrency.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import apps.makarov.com.whereismycurrency.R;
import apps.makarov.com.whereismycurrency.models.ResultOperation;
import apps.makarov.com.whereismycurrency.view.adapters.viewholders.ResultOperationViewHolder;

/**
 * Created by makarov on 30/06/15.
 */
public class HistoryAdapter extends RecyclerView.Adapter<ResultOperationViewHolder> {
    public static final String TAG = HistoryAdapter.class.getSimpleName();

    private List<ResultOperation> mHistoryItems;
    private OnClickToPresenter mListener;

    public HistoryAdapter(List<ResultOperation> historyItems) {
        this.mHistoryItems = historyItems;
    }

    public void setListener(OnClickToPresenter mListener) {
        this.mListener = mListener;
    }

    @Override
    public ResultOperationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_history_item, parent, false);
        return new ResultOperationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultOperationViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResultOperation operation = mHistoryItems.get(position);
                if(mListener != null)
                    mListener.onClick(operation);
            }
        });

        holder.bindStoryToView(mHistoryItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mHistoryItems.size();
    }

    public interface OnClickToPresenter {
        void onClick(ResultOperation operation);
    }



}
