package apps.makarov.com.whereismycurrency.interpreter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import apps.makarov.com.whereismycurrency.Utils.StringUtils;
import apps.makarov.com.whereismycurrency.models.Rate;

/**
 * Created by makarov on 28/06/15.
 */
public class RateInterpreter implements Interpreter<Rate> {

    private final JSONObject mJsonObject;

    public RateInterpreter(JSONObject jsonObject){
        this.mJsonObject = jsonObject;
    }

    @Override
    public Rate parse() {
        Rate rate = new Rate();

        try {
            String sellStr = StringUtils.getGoodLong(mJsonObject.getString("Sell"));
            double sellLong = Double.parseDouble(sellStr);
            String buyStr = StringUtils.getGoodLong(mJsonObject.getString("Buy"));
            double buyLong = Double.parseDouble(buyStr);

            rate.setBuy(buyLong);
            rate.setSell(sellLong);
            rate.setChangeRate(new Date());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rate;
    }

}
