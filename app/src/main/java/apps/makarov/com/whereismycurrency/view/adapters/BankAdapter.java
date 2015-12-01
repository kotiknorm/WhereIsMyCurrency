package apps.makarov.com.whereismycurrency.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import apps.makarov.com.whereismycurrency.R;
import apps.makarov.com.whereismycurrency.ResultUtils;
import apps.makarov.com.whereismycurrency.models.Rate;

/**
 * Created by makarov on 24.11.15.
 */
public class BankAdapter extends RecyclerView.Adapter<BankAdapter.BankViewHolder> {

    private List<Rate> mListBanks = new ArrayList<>();

    public static class BankViewHolder extends RecyclerView.ViewHolder{

        public TextView mNameBank;
        public TextView mDiffBase;
        public TextView mDiffBalance;
        public TextView mDiffBaseCurrency;
        public TextView mBalanceField;

        public BankViewHolder(View v) {
            super(v);

            mNameBank = (TextView) v.findViewById(R.id.name_bank);
            mDiffBase = (TextView) v.findViewById(R.id.diff_base);
            mDiffBalance = (TextView) v.findViewById(R.id.diff_balance);
            mDiffBaseCurrency = (TextView) v.findViewById(R.id.diff_base_currency);
            mBalanceField = (TextView) v.findViewById(R.id.balance_field);
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

        holder.mNameBank.setText(mListBanks.get(position).getBank());

        //holder.mResidueBalance.setText(mListBanks.get(position).);

        //holder.mBalanceField.setText(ResultUtils.getFinishValueStr(this, mListBanks.get(position).);
        //holder.mDiffBaseCurrency.setText(ResultUtils.getDiffStr(mListBanks.get(position).);
    }

    @Override
    public int getItemCount() {
        return mListBanks.size();
    }

}
