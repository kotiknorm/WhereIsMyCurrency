package apps.makarov.com.whereismycurrency.view.adapters.viewholders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import apps.makarov.com.whereismycurrency.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by makarov on 02/08/15.
 */
public class InactiveOperationSectionViewHolderWrapper extends ViewHolderWrapper<Void> {
    public static final String TAG = ActiveOperationSectionViewHolderWrapper.class.getSimpleName();

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.date)
    TextView date;


    public InactiveOperationSectionViewHolderWrapper(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_history_header, parent, false));
        ButterKnife.bind(this, itemView);
    }

    public void bindModelToView(Void obj) {
        title.setText("Закрытые операции");
        date.setVisibility(View.GONE);
    }

    @Override
    public void setOnClicklistener(View.OnClickListener listener) {

    }

}
