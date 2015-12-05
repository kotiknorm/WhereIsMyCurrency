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
import apps.makarov.com.whereismycurrency.models.ResultOperation;

/**
 * Created by makarov on 24.11.15.
 */
public class BankAdapter extends RecyclerView.Adapter<BankAdapter.BankViewHolder> {

    private List<Rate> mListBanks = new ArrayList<>();
    private ResultOperation mHistory;

    public static class BankViewHolder extends RecyclerView.ViewHolder{

        public TextView nameBank;
        public TextView diffBaseCurrencyValue;
        public TextView balanceField;
        public TextView diffBase;
        public TextView diffBalance;


        public BankViewHolder(View v) {
            super(v);

            nameBank = (TextView) v.findViewById(R.id.name_bank);
            diffBaseCurrencyValue = (TextView) v.findViewById(R.id.diff_base_currency);
            balanceField = (TextView) v.findViewById(R.id.balance_field);
            diffBase = (TextView) v.findViewById(R.id.diff_base);
            diffBalance = (TextView) v.findViewById(R.id.diff_balance);
        }
    }

    public BankAdapter(List<Rate> listBanks, ResultOperation history) {
        mListBanks = listBanks;
        mHistory = history;
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

        double value = mHistory.getUserHistory().getValue();
        Rate historyRate = mHistory.getUserHistory().getRate();
        Rate rate = mListBanks.get(position);

        holder.nameBank.setText(rate.getBank());

        holder.balanceField.setText
                (ResultUtils.getStrFinishBalance(holder.itemView.getContext(), value, historyRate, rate));
        holder.diffBaseCurrencyValue.setText
                (ResultUtils.getStrProfitClosingOperation(value, historyRate, rate));
        holder.diffBase.setText(ResultUtils.getStrRoundSignificant(rate.getValue()));
        holder.diffBalance.setText(ResultUtils.getStrDiffRateValue(mHistory.getExitRate(), rate));
    }

    @Override
    public int getItemCount() {
        return mListBanks.size();
    }

}
