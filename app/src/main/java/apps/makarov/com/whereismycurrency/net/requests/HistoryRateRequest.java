package apps.makarov.com.whereismycurrency.net.requests;

import org.json.JSONObject;

import java.util.List;

import apps.makarov.com.whereismycurrency.models.Rate;
import rx.Observable;

/**
 * Created by makarov on 29/06/15.
 */
public class HistoryRateRequest extends WimcRequest<Rate> {


    @Override
    public String getPath() {
        return null;
    }

    @Override
    public Observable<List<Rate>> observableJsonToStatusCode(JSONObject jsonObject) {
        return null;
    }
}
