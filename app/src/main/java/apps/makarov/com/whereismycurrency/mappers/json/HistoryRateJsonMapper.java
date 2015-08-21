package apps.makarov.com.whereismycurrency.mappers.json;

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
import apps.makarov.com.whereismycurrency.mappers.Mapper;
import apps.makarov.com.whereismycurrency.models.Bank;
import apps.makarov.com.whereismycurrency.models.CurrencyPair;
import apps.makarov.com.whereismycurrency.models.Rate;

/**
 * Created by makarov on 29/06/15.
 */

/*
 Interpreter for json to fixer.io rates
 */

public class HistoryRateJsonMapper implements Mapper<List<Rate>, JSONObject> {

    public static final String TAG = HistoryRateJsonMapper.class.getSimpleName();

    @Override
    public List<Rate> modelToData(JSONObject mJsonObject) {
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
                CurrencyPair pair = CurrencyPair.createPair(baseCurrency, compareCurrency);
                rate.setCurrencyPair(pair);
                ratesList.add(rate);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ratesList;
    }

    @Override
    public JSONObject dataToModel(List<Rate> obj) {
        return null;
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
