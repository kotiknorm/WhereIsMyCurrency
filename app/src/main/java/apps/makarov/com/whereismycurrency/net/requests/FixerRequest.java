package apps.makarov.com.whereismycurrency.net.requests;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import apps.makarov.com.whereismycurrency.models.Rate;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by makarov on 30/06/15.
 */
public class FixerRequest extends WimcRequest<Rate> {

    public static final String TAG = FixerRequest.class.getSimpleName();
    public static final String FIXER_RATES_URL = "http://fixer.io";

    @Override
    public String getPath() {
        return FIXER_RATES_URL;
    }

    @Override
    public Observable<List<Rate>> observableJsonToStatusCode(final JSONObject jsonObject) {
        return Observable.create(new Observable.OnSubscribe<List<Rate>>() {
            @Override
            public void call(Subscriber<? super List<Rate>> subscriber) {
                try {
                    List<Rate> list = parseJsonToList(jsonObject);
                    subscriber.onNext(list);
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    private List<Rate> parseJsonToList(final JSONObject jsonObj) throws JSONException {
        Log.d(TAG, jsonObj.toString());
        List<Rate> list = new ArrayList<>();

        return list;
    }

}
