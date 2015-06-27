package apps.makarov.com.whereismycurrency.net.requests;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.util.List;

import io.realm.RealmObject;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by makarov on 28/06/15.
 */
public abstract class WimcRequest<T extends RealmObject> {

    public Observable<Response> getResponse(final OkHttpClient mClient) {
        return Observable.create(new Observable.OnSubscribe<Response>() {
            @Override
            public void call(Subscriber<? super Response> subscriber) {
                try {
                    Request request = new Request.Builder()
                            .url(getPath())
                            .build();

                    Response response = mClient.newCall(request).execute();
                    subscriber.onNext(response);
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public abstract String getPath();

    public abstract Observable<List<T>> observableJsonToStatusCode(final JSONObject jsonObject);

}
