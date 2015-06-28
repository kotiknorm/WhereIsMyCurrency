package apps.makarov.com.whereismycurrency.interpreter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Iterator;

import apps.makarov.com.whereismycurrency.models.Bank;
import apps.makarov.com.whereismycurrency.models.Rate;

/**
 * Created by makarov on 28/06/15.
 */

public class BankInterpreter implements Interpreter<Bank> {

    public static final String RUB_CODE = "RUB";
    public static final String EUR_CODE = "EUR";
    public static final String USD_CODE = "USD";

    private final JSONObject mJsonObject;

    public BankInterpreter(JSONObject jsonObject){
        this.mJsonObject = jsonObject;
    }

    @Override
    public Bank parse() {
        Bank bank = getBaseBankInterpreter();

        try {
            JSONObject jsonObj = new JSONObject();
            Iterator<String> iter = jsonObj.keys();

            while(iter.hasNext()){

                String compareCurrency = iter.next();
                RateInterpreter parseRate = new RateInterpreter(jsonObj.getJSONObject(compareCurrency));

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
            bank.setChangeRate(new Date());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return bank;
    }


}
