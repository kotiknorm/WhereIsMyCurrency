package apps.makarov.com.whereismycurrency.net;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.List;

import apps.makarov.com.whereismycurrency.repository.IRepository;
import apps.makarov.com.whereismycurrency.net.requests.WimcRequest;
import io.realm.RealmObject;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by makarov on 29/06/15.
 */

public class RequestService {

    private OkHttpClient mClient;
    private IRepository mStore;

    public RequestService(OkHttpClient client, IRepository store) {
        this.mClient = client;
        this.mStore = store;
    }

    protected IRepository getStore(){
        return mStore;
    }

    protected <T extends RealmObject> Observable<List<T>> getObservableRequest(final WimcRequest request, final Observable<List<T>> observableDateFromLocalStore) {
        boolean urlInCache = mStore.hasUrlInCache(request.getPath());

        return urlInCache ? observableDateFromLocalStore :
                observableNetwork(request)
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

    private Observable<List<? extends RealmObject>> observableNetwork(final WimcRequest request) {
        return getResponse(request)
                .flatMap(new Func1<Response, Observable<String>>() {
                    @Override
                    public Observable<String> call(Response response) {
                        return responseToString(response);
                    }
                }).flatMap(new Func1<String, Observable<List<? extends RealmObject>>>() {
                    @Override
                    public Observable<List<? extends RealmObject>> call(String s) {
                        return request.observableStringToObjectsList(s);
                    }
                });
    }

    private final Observable<Response> getResponse(final WimcRequest wimcRequest) {
        return Observable.create(new Observable.OnSubscribe<Response>() {
            @Override
            public void call(Subscriber<? super Response> subscriber) {
                try {
                    Request request = wimcRequest.getRequest();
                    Response response = mClient.newCall(request).execute();

                    subscriber.onNext(response);
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        });
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
