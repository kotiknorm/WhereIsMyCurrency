package apps.makarov.com.whereismycurrency.net.requests;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import apps.makarov.com.whereismycurrency.interpreter.BankInterpreter;
import apps.makarov.com.whereismycurrency.models.Bank;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by makarov on 28/06/15.
 */
public class BankRequest extends WimcRequest<Bank> {

    public static final String TAG = FixerRequest.class.getSimpleName();
    public static final String BANK_RATES_URL = "http://informer.kovalut.ru/webmaster/xml-table.php?kod=7701";

    @Override
    public String getPath() {
        return BANK_RATES_URL;
    }

    @Override
    public Observable<List<Bank>> observableJsonToStatusCode(final JSONObject jsonObject) {
        return Observable.create(new Observable.OnSubscribe<List<Bank>>() {
            @Override
            public void call(Subscriber<? super List<Bank>> subscriber) {
                try {
                    List<Bank> list = parseJsonToList(jsonObject);
                    subscriber.onNext(list);
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    private List<Bank> parseJsonToList(JSONObject jsonObj) throws JSONException {

        JSONArray actualRates = jsonObj.getJSONObject("Exchange_Rates").getJSONObject("Actual_Rates").getJSONArray("Bank");
        Log.d(TAG, actualRates.toString());
        List<Bank> list = new ArrayList<>();

        for (int i = 0; i < actualRates.length(); i++) {

            BankInterpreter interpreter = new BankInterpreter(actualRates.getJSONObject(i));
            Bank bank = interpreter.parse();
            list.add(bank);
        }

        return list;
    }

}
