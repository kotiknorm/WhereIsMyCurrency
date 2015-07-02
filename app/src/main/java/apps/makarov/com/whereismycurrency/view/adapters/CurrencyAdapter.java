package apps.makarov.com.whereismycurrency.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import apps.makarov.com.whereismycurrency.R;
import apps.makarov.com.whereismycurrency.view.adapters.viewholders.CurrencyPairHolder;

/**
 * Created by makarov on 02/07/15.
 */
public class CurrencyAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    public CurrencyAdapter(Context context){
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return 100;
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

        return rowView;
    }
}
