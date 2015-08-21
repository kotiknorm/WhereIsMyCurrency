package apps.makarov.com.whereismycurrency.net.requests;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apps.makarov.com.whereismycurrency.DateUtils;
import apps.makarov.com.whereismycurrency.interpreter.HistoryRateInterpreter;
import apps.makarov.com.whereismycurrency.models.RateData;

/**
 * Created by makarov on 30/06/15.
 */
public class FixerRequest extends WimcRequest<RateData> {

    public static final String TAG = FixerRequest.class.getSimpleName();
    public static final String FIXER_RATES_URL = "http://api.fixer.io";

    public final String mBaseCurrency;
    public final Date mDate;


    public FixerRequest(String baseCurrency, Date date){
        this.mBaseCurrency = baseCurrency;
        this.mDate = date;
    }

    private String getCorrectDateFormat(){
        SimpleDateFormat dt = DateUtils.getFixerIoDareFormat();
        return dt.format(mDate);
    }

    @Override
    public String getPath() {
        return FIXER_RATES_URL + "/" + getCorrectDateFormat();
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
    protected List<RateData> parseStringToList(final String str) throws JSONException {
        Log.d(TAG, str);
        JSONObject result = new JSONObject(str);

        HistoryRateInterpreter interpreter = new HistoryRateInterpreter(result);
        List<RateData> list = interpreter.parse();
        return list;
    }


}
