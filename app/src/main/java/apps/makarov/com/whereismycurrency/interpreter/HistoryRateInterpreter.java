package apps.makarov.com.whereismycurrency.interpreter;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import apps.makarov.com.whereismycurrency.DateUtils;
import apps.makarov.com.whereismycurrency.models.CurrencyPair;
import apps.makarov.com.whereismycurrency.models.Bank;
import apps.makarov.com.whereismycurrency.models.Rate;

/**
 * Created by makarov on 29/06/15.
 */

/*
 Interpreter for json to fixer.io rates
 */

public class HistoryRateInterpreter implements Interpreter<List<Rate>> {

    public static final String TAG = HistoryRateInterpreter.class.getSimpleName();
    private final JSONObject mJsonObject;

    public HistoryRateInterpreter(JSONObject jsonObject) {
        this.mJsonObject = jsonObject;
    }

    @Override
    public List<Rate> parse() {
        List<Rate> ratesList = new ArrayList<>();

        try {
            String baseCurrency = mJsonObject.getString("base");
            String dateStr = mJsonObject.getString("date");
            Date date = parseFixerDate(dateStr);
            JSONObject ratesJsonArray = mJsonObject.getJSONObject("rates");

            Iterator<String> iter = ratesJsonArray.keys();
            while (iter.hasNext()) {
                String compareCurrency = iter.next();
                double value = ratesJsonArray.getDouble(compareCurrency);

                Rate rate = new Rate();
                rate.setValue(value);
                rate.setChangeRate(date);
                rate.setBank(Bank.DEFAULT);
                CurrencyPair pair = CurrencyPair.createPair(compareCurrency, baseCurrency);
                rate.setCurrencyPair(pair);
                rate.setKey(Rate.generateKey(rate));
                ratesList.add(rate);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ratesList;
    }

    private Date parseFixerDate(String dateStr) {
        try {
            SimpleDateFormat dt = DateUtils.getFixerIoDareFormat();
            return dt.parse(dateStr);
        } catch (ParseException e) {
            Log.e(TAG, "ParseException", e);
            return new Date();
        }
    }
}
