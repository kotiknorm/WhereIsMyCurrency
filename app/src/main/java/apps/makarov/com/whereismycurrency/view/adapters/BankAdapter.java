package apps.makarov.com.whereismycurrency.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import apps.makarov.com.whereismycurrency.R;
import apps.makarov.com.whereismycurrency.models.Rate;

/**
 * Created by makarov on 24.11.15.
 */
public class BankAdapter extends RecyclerView.Adapter<BankAdapter.BankViewHolder> {

    private List<Rate> mListBanks = new ArrayList<>();

    public static class BankViewHolder extends RecyclerView.ViewHolder{

        public TextView mBaseValue;
        public TextView mDiffBaseCurrency;
        public TextView mBalanceField;
        public TextView mTextView;
        public TextView mChangeBalance;
        public TextView mResidueBalance;

        public BankViewHolder(View v) {
            super(v);

            mBaseValue = (TextView) v.findViewById(R.id.base_value);
            mDiffBaseCurrency = (TextView) v.findViewById(R.id.diff_base_currency);
            mBalanceField = (TextView) v.findViewById(R.id.balance_field);
            mTextView = (TextView) v.findViewById(R.id.textView);
            mChangeBalance = (TextView) v.findViewById(R.id.change_balance);
            mResidueBalance = (TextView) v.findViewById(R.id.residue_balance);
        }
    }

    public BankAdapter(List<Rate> listBanks) {
        mListBanks = listBanks;
    }

    @Override
    public BankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bank_item_layout, parent, false);

        BankViewHolder bankViewHolder = new BankViewHolder(view);
        return bankViewHolder;
    }

    @Override
    public void onBindViewHolder(BankViewHolder holder, int position) {

        holder.mBaseValue.setText(mListBanks.get(position).getBank());

        //holder.mDiffBaseCurrency.setText(mListBanks.get(position).getCurrencyPair().getBaseCurrency());
        //holder.mBalanceField.setText(mListBanks.get(position).getCurrencyPair().getCompareCurrency());
        //holder.mTextView.setText(mListBanks.get(position).getChangeRate().toString());
        //holder.mChangeBalance.setText((int) mListBanks.get(position).getValue());
        //holder.mResidueBalance.setText(mListBanks.get(position).);
    }

    @Override
    public int getItemCount() {
        return mListBanks.size();
    }

}
