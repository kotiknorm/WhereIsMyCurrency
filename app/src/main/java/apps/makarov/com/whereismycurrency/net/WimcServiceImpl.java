package apps.makarov.com.whereismycurrency.net;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;

import org.json.JSONObject;
import org.json.XML;

import java.util.List;

import apps.makarov.com.whereismycurrency.database.IStore;
import apps.makarov.com.whereismycurrency.models.Bank;
import apps.makarov.com.whereismycurrency.models.Rate;
import apps.makarov.com.whereismycurrency.models.UserHistory;
import apps.makarov.com.whereismycurrency.net.requests.BankRequest;
import apps.makarov.com.whereismycurrency.net.requests.WimcRequest;
import io.realm.RealmObject;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by makarov on 26/06/15.
 */

public class WimcServiceImpl implements WimcService {

    private OkHttpClient mClient;
    private IStore mStore;

    public WimcServiceImpl(OkHttpClient client, IStore store) {
        this.mClient = client;
        this.mStore = store;
    }

    @Override
    public Observable<List<Rate>> getRateFromBank(String bankName) {
        return null;
    }

    @Override
    public Observable<List<Bank>> getAllBank() {
        final WimcRequest bankRequest = new BankRequest();

        Observable<List<Bank>> observable = Observable.create(new Observable.OnSubscribe<List<Bank>>() {
            @Override
            public void call(Subscriber<? super List<Bank>> subscriber) {
                try {
                    List<Bank> list = mStore.getBanks();

                    subscriber.onNext(list);
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread());

        return getObservableRequest(bankRequest, observable);
    }


    @Override
    public Observable<List<UserHistory>> getHistory() {
        return Observable.create(new Observable.OnSubscribe<List<UserHistory>>() {
            @Override
            public void call(Subscriber<? super List<UserHistory>> subscriber) {
                try {
                    List<UserHistory> list = mStore.getUserHistory();

                    subscriber.onNext(list);
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void addHistoryItem(UserHistory historyItem) {
        mStore.saveObject(historyItem);
    }


    private <T extends RealmObject> Observable<List<T>> getObservableRequest(final WimcRequest request, final Observable<List<T>> observableDateFromLocalStore) {
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

