package apps.makarov.com.whereismycurrency.interpreter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import apps.makarov.com.whereismycurrency.DateUtils;
import apps.makarov.com.whereismycurrency.StringUtils;
import apps.makarov.com.whereismycurrency.models.CurrencyPair;
import apps.makarov.com.whereismycurrency.models.Rate;

/**
 * Created by makarov on 28/06/15.
 */
public class RateInterpreter implements Interpreter<List<Rate>> {

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
    public List<Rate> parse() {
        List<Rate> ratesList = new ArrayList<>();
        try {
            Rate rate = new Rate();
            String buyStr = StringUtils.getGoodLong(mJsonObject.getString("Buy"));
            double buyLong = Double.parseDouble(buyStr);
            rate.setValue(buyLong);
            rate.setChangeRate(DateUtils.getTodayDate());
            rate.setCurrencyPair(CurrencyPair.createPair(baseCurrency, compareCurrency));
            rate.setBank(bankName);
            rate.setKey(Rate.generateKey(rate));
            ratesList.add(rate);

            Rate rateInvert = new Rate();
            String sellStr = StringUtils.getGoodLong(mJsonObject.getString("Sell"));
            double sellLong = Double.parseDouble(sellStr);
            rateInvert.setValue(1 / sellLong);
            rateInvert.setChangeRate(DateUtils.getTodayDate());
            rateInvert.setCurrencyPair(CurrencyPair.createPair(compareCurrency, baseCurrency));
            rate.setBank(bankName);
            rateInvert.setKey(Rate.generateKey(rateInvert));
            ratesList.add(rateInvert);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ratesList;
    }

}
