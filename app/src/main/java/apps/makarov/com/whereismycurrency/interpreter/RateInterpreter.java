package apps.makarov.com.whereismycurrency.interpreter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import apps.makarov.com.whereismycurrency.DateUtils;
import apps.makarov.com.whereismycurrency.StringUtils;
import apps.makarov.com.whereismycurrency.models.CurrencyPairData;
import apps.makarov.com.whereismycurrency.models.RateData;

/**
 * Created by makarov on 28/06/15.
 */
public class RateInterpreter implements Interpreter<List<RateData>> {

    private final JSONObject mJsonObject;
    private final String baseCurrency;
    private final String compareCurrency;
    private final String bankName;

    public RateInterpreter(String baseCurrency, String compareCurrency, JSONObject jsonObject, String bankName){
        this.mJsonObject = jsonObject;
        this.baseCurrency = baseCurrency;
        this.compareCurrency = compareCurrency;
        this.bankName = bankName;
    }

    @Override
    public List<RateData> parse() {
        List<RateData> ratesList = new ArrayList<>();
        try {
            RateData rate = new RateData();
            String buyStr = StringUtils.getGoodLong(mJsonObject.getString("Buy"));
            double buyLong = Double.parseDouble(buyStr);
            rate.setValue(buyLong);
            rate.setChangeRate(DateUtils.getTodayDate());
            rate.setCurrencyPair(CurrencyPairData.createPair(baseCurrency, compareCurrency));
            rate.setBank(bankName);
            ratesList.add(rate);

            RateData rateInvert = new RateData();
            String sellStr = StringUtils.getGoodLong(mJsonObject.getString("Sell"));
            double sellLong = Double.parseDouble(sellStr);
            rateInvert.setValue(1 / sellLong);
            rateInvert.setChangeRate(DateUtils.getTodayDate());
            rateInvert.setCurrencyPair(CurrencyPairData.createPair(compareCurrency, baseCurrency));
            rate.setBank(bankName);
            ratesList.add(rateInvert);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ratesList;
    }

}
