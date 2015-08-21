package apps.makarov.com.whereismycurrency.mappers.json;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import apps.makarov.com.whereismycurrency.DateUtils;
import apps.makarov.com.whereismycurrency.StringUtils;
import apps.makarov.com.whereismycurrency.mappers.Mapper;
import apps.makarov.com.whereismycurrency.models.CurrencyPair;
import apps.makarov.com.whereismycurrency.models.Rate;

/**
 * Created by makarov on 28/06/15.
 */
public class RateJsonMapper implements Mapper<List<Rate>, JSONObject> {

    private final String baseCurrency;
    private final String compareCurrency;
    private final String bankName;

    public RateJsonMapper(String baseCurrency, String compareCurrency, String bankName){
        this.baseCurrency = baseCurrency;
        this.compareCurrency = compareCurrency;
        this.bankName = bankName;
    }

    @Override
    public List<Rate> modelToData(JSONObject mJsonObject) {
        List<Rate> ratesList = new ArrayList<>();
        try {
            Rate rate = new Rate();
            String buyStr = StringUtils.getGoodLong(mJsonObject.getString("Buy"));
            double buyLong = Double.parseDouble(buyStr);
            rate.setValue(buyLong);
            rate.setChangeRate(DateUtils.getTodayDate());
            rate.setCurrencyPair(CurrencyPair.createPair(baseCurrency, compareCurrency));
            rate.setBank(bankName);
            ratesList.add(rate);

            Rate rateInvert = new Rate();
            String sellStr = StringUtils.getGoodLong(mJsonObject.getString("Sell"));
            double sellLong = Double.parseDouble(sellStr);
            rateInvert.setValue(1 / sellLong);
            rateInvert.setChangeRate(DateUtils.getTodayDate());
            rateInvert.setCurrencyPair(CurrencyPair.createPair(compareCurrency, baseCurrency));
            rate.setBank(bankName);
            ratesList.add(rateInvert);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ratesList;
    }

    @Override
    public JSONObject dataToModel(List<Rate> obj) {
        return null;
    }
}
