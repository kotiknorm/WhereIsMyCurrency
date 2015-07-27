package apps.makarov.com.whereismycurrency.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import apps.makarov.com.whereismycurrency.R;
import apps.makarov.com.whereismycurrency.view.adapters.viewholders.CurrencyPairHolder;

/**
 * Created by makarov on 02/07/15.
 */
public class CurrencyAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<String> mListCurrency;

    public CurrencyAdapter(Context context, List<String> listCurrency){
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mListCurrency = listCurrency;
    }

    @Override
    public int getCount() {
        return mListCurrency.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CurrencyPairHolder holder;

        View rowView = convertView;
        if (rowView == null) {
            rowView = mInflater.inflate(R.layout.currency_list_item, null, true);
            holder = new CurrencyPairHolder(rowView);
            rowView.setTag(holder);
        } else {
            holder = (CurrencyPairHolder) rowView.getTag();
        }

        holder.bindCurrencyToView(mContext, mListCurrency.get(position));

        return rowView;
    }
}
