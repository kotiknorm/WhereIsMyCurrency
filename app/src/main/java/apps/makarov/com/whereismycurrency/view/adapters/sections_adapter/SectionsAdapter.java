package apps.makarov.com.whereismycurrency.view.adapters.sections_adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import apps.makarov.com.whereismycurrency.view.adapters.HistoryAdapter;
import apps.makarov.com.whereismycurrency.view.adapters.sections_adapter.holder.ModelWrapper;
import apps.makarov.com.whereismycurrency.view.adapters.viewholders.ViewHolderWrapper;

/**
 * Created by makarov on 02/08/15.
 */
public abstract class SectionsAdapter<T extends ViewHolderWrapper> extends RecyclerView.Adapter<T> {
    public static final String TAG = HistoryAdapter.class.getSimpleName();

    private List<ModelWrapper> mModelWrapperList = new ArrayList<>();

    public SectionsAdapter(List<ModelWrapper> modelWrapperList) {
        mModelWrapperList = modelWrapperList;
    }

    @Override
    public int getItemViewType(int position) {
        return mModelWrapperList.get(position).getType();
    }

    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        return getHolder(parent, viewType);
    }

    protected abstract T getHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(ViewHolderWrapper holder, final int position) {
        holder.bindModelToView(getWrapperModel(position));
        createdListeners(holder, getWrapperModel(position), getItemViewType(position));
    }

    protected abstract void createdListeners(ViewHolderWrapper holder, Object object, int type);

    private Object getWrapperModel(int position){
        return mModelWrapperList.get(position).getModel();
    }

    @Override
    public int getItemCount() {
        return mModelWrapperList.size();
    }

}

