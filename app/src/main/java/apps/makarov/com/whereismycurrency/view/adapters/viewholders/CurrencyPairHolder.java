package apps.makarov.com.whereismycurrency.view.adapters.viewholders;

import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by makarov on 02/07/15.
 */

public class CurrencyPairHolder {

    public static final String TAG = CurrencyPairHolder.class.getSimpleName();

    public CurrencyPairHolder(View itemView) {
        ButterKnife.inject(this, itemView);
    }

    public void bindCurrencyToView() {
    }
}
