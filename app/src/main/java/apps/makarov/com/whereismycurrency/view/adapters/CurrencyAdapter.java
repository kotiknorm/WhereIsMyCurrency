package apps.makarov.com.whereismycurrency.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import apps.makarov.com.whereismycurrency.R;
import apps.makarov.com.whereismycurrency.models.CurrencyPair;
import apps.makarov.com.whereismycurrency.view.adapters.viewholders.CurrencyPairHolder;

/**
 * Created by makarov on 02/07/15.
 */
public class CurrencyAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<CurrencyPair> mListPairs;

    public CurrencyAdapter(Context context, List<CurrencyPair> listPairs){
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mListPairs = listPairs;
    }

    @Override
    public int getCount() {
        return mListPairs.size();
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

        holder.bindCurrencyToView(mListPairs.get(position));

        return rowView;
    }
}
