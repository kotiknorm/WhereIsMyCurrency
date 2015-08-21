package apps.makarov.com.whereismycurrency.view.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by makarov on 30/06/15.
 */
public abstract class ViewHolderWrapper<T> extends RecyclerView.ViewHolder {

    public ViewHolderWrapper(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public abstract void bindModelToView(T history);

    public abstract void setOnClicklistener(View.OnClickListener listener);

}
