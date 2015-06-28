package apps.makarov.com.whereismycurrency.net;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;

import org.json.JSONObject;
import org.json.XML;

import java.util.List;

import apps.makarov.com.whereismycurrency.database.IStore;
import apps.makarov.com.whereismycurrency.net.requests.WimcRequest;
import io.realm.RealmObject;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by makarov on 29/06/15.
 */

public class RequestService {

    private OkHttpClient mClient;
    protected IStore mStore;

    public RequestService(OkHttpClient client, IStore store) {
        this.mClient = client;
        this.mStore = store;
    }

    protected <T extends RealmObject> Observable<List<T>> getObservableRequest(final WimcRequest request, final Observable<List<T>> observableDateFromLocalStore) {
        boolean urlInCache = mStore.isUrlInCache(request.getPath());

        return urlInCache ? observableDateFromLocalStore :
                observableNetwork(request)
                        .flatMap(new Func1<JSONObject, Observable<List<? extends RealmObject>>>() {
                            @Override
                            public Observable<List<? extends RealmObject>> call(JSONObject s) {
                                return request.observableJsonToStatusCode(s);
                            }
                        })
                        .flatMap(new Func1<List<? extends RealmObject>, Observable<Exception>>() {
                            @Override
                            public Observable<Exception> call(List<? extends RealmObject> list) {
                                return observableSaveObjects(list);
                            }
                        })
                        .flatMap(new Func1<Exception, Observable<List<T>>>() {
                            @Override
                            public Observable<List<T>> call(Exception e) {
                                return observableDateFromLocalStore;
                            }
                        });
    }

    private Observable<JSONObject> observableNetwork(WimcRequest request) {
        return request.getResponse(mClient)
                .doOnNext(cachingRequest())
                .flatMap(new Func1<Response, Observable<String>>() {
                    @Override
                    public Observable<String> call(Response response) {
                        return responseToString(response);
                    }
                }).flatMap(new Func1<String, Observable<JSONObject>>() {
                    @Override
                    public Observable<JSONObject> call(String s) {
                        return stringToJson(s);
                    }
                });
    }

    private Action1 cachingRequest() {
        return new Action1<Response>() {
            @Override
            public void call(Response response) {
                mStore.addUrlToCache(response.request().urlString());
            }
        };
    }

    private Observable<String> responseToString(final Response response) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    subscriber.onNext(response.body().string());
                    subscriber.onCompleted();

                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    private Observable<JSONObject> stringToJson(final String xml) {
        return Observable.create(new Observable.OnSubscribe<JSONObject>() {
            @Override
            public void call(Subscriber<? super JSONObject> subscriber) {
                try {
                    JSONObject jsonObj = XML.toJSONObject(xml);

                    subscriber.onNext(jsonObj);
                    subscriber.onCompleted();

                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    private Observable<Exception> observableSaveObjects(final List<? extends RealmObject> list) {
        return Observable.create(new Observable.OnSubscribe<Exception>() {
            @Override
            public void call(Subscriber<? super Exception> subscriber) {
                try {
                    for (RealmObject object : list) {
                        mStore.saveObject(object);
                    }
                    subscriber.onNext(null);
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        });
    }

}
