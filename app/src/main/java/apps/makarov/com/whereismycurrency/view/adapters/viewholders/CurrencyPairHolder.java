package apps.makarov.com.whereismycurrency.view.adapters.viewholders;

import android.view.View;
import android.widget.TextView;

import apps.makarov.com.whereismycurrency.R;
import apps.makarov.com.whereismycurrency.models.CurrencyPair;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by makarov on 02/07/15.
 */

public class CurrencyPairHolder {

    public static final String TAG = CurrencyPairHolder.class.getSimpleName();

    @InjectView(R.id.pair)
    TextView pairTextview;

    public CurrencyPairHolder(View itemView) {
        ButterKnife.inject(this, itemView);
    }

    public void bindCurrencyToView(CurrencyPair pair) {
        pairTextview.setText(pair.getBaseCurrency() + "_" + pair.getCompareCurrency());
    }
}
