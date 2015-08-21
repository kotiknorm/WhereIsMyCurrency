package apps.makarov.com.whereismycurrency.interpreter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import apps.makarov.com.whereismycurrency.models.BankData;
import apps.makarov.com.whereismycurrency.models.RateData;

/**
 * Created by makarov on 28/06/15.
 */

public class BankInterpreter implements Interpreter<BankData> {

    private final JSONObject mJsonObject;

    public BankInterpreter(JSONObject jsonObject){
        this.mJsonObject = jsonObject;
    }

    @Override
    public BankData parse() {
        BankData bank = getBaseBankInterpreter();

        try {
            Iterator<String> iter = mJsonObject.keys();

            while(iter.hasNext()){
                String compareCurrency = iter.next();

                if(!RateData.getCodesList().contains(compareCurrency))
                    continue;

                RateInterpreter parseRate = new RateInterpreter(RateData.RUB_CODE, compareCurrency,
                        mJsonObject.getJSONObject(compareCurrency), bank.getName());
                bank.getRates().addAll(parseRate.parse());

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bank;
    }

    private BankData getBaseBankInterpreter() {
        BankData bank = new BankData();

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
