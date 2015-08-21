package apps.makarov.com.whereismycurrency.view.adapters.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import apps.makarov.com.whereismycurrency.R;
import apps.makarov.com.whereismycurrency.models.Rate;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by makarov on 02/07/15.
 */

public class CurrencyPairHolder {

    public static final String TAG = CurrencyPairHolder.class.getSimpleName();

    @Bind(R.id.currency_field)
    TextView currencyTextView;
    @Bind(R.id.icon_currency_field)
    ImageView iconCurrency;

    public CurrencyPairHolder(View itemView) {
        ButterKnife.bind(this, itemView);
    }

    public void bindCurrencyToView(Context context, String currency) {
        currencyTextView.setText(currency);
        iconCurrency.setImageDrawable(Rate.getCurrencyIcon(context, currency));
    }
}
