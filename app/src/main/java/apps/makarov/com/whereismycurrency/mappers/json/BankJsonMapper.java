package apps.makarov.com.whereismycurrency.mappers.json;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import apps.makarov.com.whereismycurrency.mappers.Mapper;
import apps.makarov.com.whereismycurrency.models.Bank;
import apps.makarov.com.whereismycurrency.models.Rate;

/**
 * Created by makarov on 28/06/15.
 */

public class BankJsonMapper implements Mapper<Bank, JSONObject> {

    @Override
    public Bank dataToModel(JSONObject mJsonObject) {
        Bank bank = getBaseBankInterpreter(mJsonObject);

        try {
            Iterator<String> iter = mJsonObject.keys();

            while(iter.hasNext()){
                String compareCurrency = iter.next();

                if(!Rate.getCodesList().contains(compareCurrency))
                    continue;

                RateJsonMapper parseRate = new RateJsonMapper(Rate.RUB_CODE, compareCurrency, bank.getName());
                bank.getRates().addAll(parseRate.dataToModel(mJsonObject.getJSONObject(compareCurrency)));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bank;
    }

    @Override
    public JSONObject modelToData(Bank obj) {
        return null;
    }

    private Bank getBaseBankInterpreter(JSONObject mJsonObject) {
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
