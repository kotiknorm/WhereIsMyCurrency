package apps.makarov.com.whereismycurrency.interpreter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import apps.makarov.com.whereismycurrency.models.Bank;
import apps.makarov.com.whereismycurrency.models.Rate;

/**
 * Created by makarov on 28/06/15.
 */

public class BankInterpreter implements Interpreter<Bank> {


    private final JSONObject mJsonObject;

    public BankInterpreter(JSONObject jsonObject){
        this.mJsonObject = jsonObject;
    }

    @Override
    public Bank parse() {
        Bank bank = getBaseBankInterpreter();

        try {
            Iterator<String> iter = mJsonObject.keys();

            while(iter.hasNext()){
                String compareCurrency = iter.next();

                if(!Rate.getCodesList().contains(compareCurrency))
                    continue;

                RateInterpreter parseRate = new RateInterpreter(Rate.RUB_CODE, compareCurrency,
                        mJsonObject.getJSONObject(compareCurrency), bank.getName());
                bank.getRates().addAll(parseRate.parse());

            }

            bank.setKey(Bank.generateKey(bank));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bank;
    }

    private Bank getBaseBankInterpreter() {
        Bank bank = new Bank();

        try {
            String nameBank = mJsonObject.getString("Name");
            String changeTime = mJsonObject.getString("ChangeTime");
            bank.setName(nameBank);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return bank;
    }


}
