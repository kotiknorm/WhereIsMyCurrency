package apps.makarov.com.whereismycurrency.net.requests;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apps.makarov.com.whereismycurrency.models.Rate;

/**
 * Created by makarov on 30/06/15.
 */
public class FixerRequest extends WimcRequest<Rate> {

    public static final String TAG = FixerRequest.class.getSimpleName();
    public static final String FIXER_RATES_URL = "http://api.fixer.io";

    public final String mBaseCurrency;
    public final Date mDate;


    public FixerRequest(String baseCurrency, Date date){
        this.mBaseCurrency = baseCurrency;
        this.mDate = date;
    }

    @Override
    public String getPath() {
        return FIXER_RATES_URL + "/" + mDate.toString();
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put("base", mBaseCurrency);
        return params;
    }

    @Override
    protected TYPE getType() {
        return TYPE.GET;
    }

    @Override
    protected List<Rate> parseJsonToList(final JSONObject jsonObj) throws JSONException {
        Log.d(TAG, jsonObj.toString());
        List<Rate> list = new ArrayList<>();

        return list;
    }

}