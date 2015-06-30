package apps.makarov.com.whereismycurrency.net.requests;

import com.squareup.okhttp.Request;

import org.json.JSONException;

import java.util.List;
import java.util.Map;

import io.realm.RealmObject;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by makarov on 28/06/15.
 */
public abstract class WimcRequest<T extends RealmObject> {

    enum TYPE {
        GET, POST
    }

    public abstract String getPath();

    public Observable<List<T>> observableStringToObjectsList(final String jsonObject) {
        return Observable.create(new Observable.OnSubscribe<List<T>>() {
            @Override
            public void call(Subscriber<? super List<T>> subscriber) {
                try {
                    List<T> list = parseStringToList(jsonObject);
                    subscriber.onNext(list);
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public Request getRequest() {

        Request.Builder builder = new Request.Builder();
        TYPE typeRequest = getType();
        switch (typeRequest) {
            case GET:
                builder = builder.url(getPath() + getStringUrlParams(getParams()));
                break;
            case POST:
                builder = builder.url(getPath());
                break;
        }
        return builder.build();
    }

    protected abstract List<T> parseStringToList(String jsonObj)  throws JSONException;

    protected abstract TYPE getType();

    protected Map<String, String> getParams() {
        return null;
    }

    public static String getStringUrlParams(Map<String, String> params) {
        StringBuilder paramsStr = new StringBuilder("");

        if (params == null || params.size() == 0) {
            return paramsStr.toString();
        }

        paramsStr.append("?");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            paramsStr.append(entry.getKey() + "=" + entry.getValue());
            paramsStr.append("&");
        }
        return paramsStr.substring(0, params.size() - 1);
    }

}
