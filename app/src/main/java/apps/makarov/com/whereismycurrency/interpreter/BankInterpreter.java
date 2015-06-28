package apps.makarov.com.whereismycurrency.interpreter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import apps.makarov.com.whereismycurrency.models.Bank;
import apps.makarov.com.whereismycurrency.models.Rate;

/**
 * Created by makarov on 28/06/15.
 */

public class BankInterpreter implements Interpreter<Bank> {

    public static List<String> codesCurrency = new ArrayList<>();

    public static final String RUB_CODE = "RUB";
    public static final String EUR_CODE = "EUR";
    public static final String USD_CODE = "USD";

    static {
        codesCurrency.add(RUB_CODE);
        codesCurrency.add(EUR_CODE);
        codesCurrency.add(USD_CODE);
    }

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

                if(!codesCurrency.contains(compareCurrency))
                    continue;

                RateInterpreter parseRate = new RateInterpreter(mJsonObject.getJSONObject(compareCurrency));
                Rate rate = parseRate.parse();
                rate.setBaseCurrency(RUB_CODE);
                rate.setCompareCurrency(compareCurrency);
                rate.setKey(Rate.generateKey(bank.getName(), rate));
                bank.getRates().add(rate);

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
