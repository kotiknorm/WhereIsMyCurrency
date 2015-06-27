package apps.makarov.com.whereismycurrency.net;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import apps.makarov.com.whereismycurrency.database.IStore;
import apps.makarov.com.whereismycurrency.models.Bank;
import apps.makarov.com.whereismycurrency.models.Rate;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by makarov on 26/06/15.
 */

public class GMHServiceImpl implements GMHService {

    private OkHttpClient mClient;

    @Inject
    public IStore mStore;

    public GMHServiceImpl(OkHttpClient client, IStore store) {
        this.mClient = client;
        this.mStore = store;
    }

    @Override
    public Observable<List<Bank>> getAllBank(String requestUrl) {
        return mapResponseToList(requestUrl)
//                .flatMap(new Func1<Response, Observable<List<Bank>>>() {
//                    @Override
//                    public Observable<List<Bank>> call(Response response) {
//                        return mapResponseToXML(response);
//                    }
//                })
//                .flatMap(new Func1<String, Observable<List<Bank>>>() {
//                    @Override
//                    public Observable<List<Bank>> call(final String unparsedXml) {
//                        return xmlResponseToListObjects(unparsedXml);
//                    }
//                })
                ;
    }


    @Override
    public Observable<List<Rate>> getRateFromBank(String bankName) {
        return null;
    }

    private Observable<Response> getResponse(final String requestUrl) {
        return Observable.create(new Observable.OnSubscribe<Response>() {
            @Override
            public void call(Subscriber<? super Response> subscriber) {
                try {
                    Request request = new Request.Builder()
                            .url(requestUrl)
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

    private Observable<List<Bank>> mapResponseToList(final String requestUrl) {
        return Observable.create(new Observable.OnSubscribe<List<Bank>>() {
            @Override
            public void call(Subscriber<? super List<Bank>> subscriber) {
                try {
                    Request request = new Request.Builder()
                            .url(requestUrl)
                            .build();

                    Response response = mClient.newCall(request).execute();

                    String respBody = null;
                    if (response.body() != null) {
                        respBody = response.body().string();
                        response.body().close();
                    }

                    //parse xml
                    JSONObject jsonObj = xmlToJson(respBody);
                    List<Bank> list = jsonToListObj(jsonObj);
                    Log.d("","");

                    subscriber.onNext(list);
                    subscriber.onCompleted();

                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public JSONObject xmlToJson(String xml) throws JSONException {
        JSONObject jsonObj = XML.toJSONObject(xml);
        return jsonObj;
    }

    public Bank jsonToBank(JSONObject jsonObj) throws JSONException {
        Bank bank = new Bank();

        JSONObject objectEur = jsonObj.getJSONObject("EUR");
        Rate eurRate = jsonToRateObject(objectEur, "RUB", "EUR");
        bank.getRates().add(eurRate);

        JSONObject objectUsd = jsonObj.getJSONObject("USD");
        Rate usdRate = jsonToRateObject(objectUsd, "USD", "EUR");
        bank.getRates().add(usdRate);

        String nameBank = jsonObj.getString("Name");
        String changeTime = jsonObj.getString("ChangeTime");

        bank.setName(nameBank);
        bank.setChangeRate(new Date());
        return bank;

    }

    public List<Bank> jsonToListObj(JSONObject jsonObj) throws XmlPullParserException, IOException, JSONException {

        JSONArray actualRates = jsonObj.getJSONObject("Exchange_Rates").getJSONObject("Actual_Rates").getJSONArray("Bank");
        Log.d("JSON", actualRates.toString());
        List<Bank> banksList = new ArrayList<>(actualRates.length());

        mStore.beginTransaction();
        for (int i = 0; i < actualRates.length(); i++) {
            Bank bank = jsonToBank(actualRates.getJSONObject(i));
            mStore.saveObject(bank);
            banksList.add(bank);
        }

        mStore.commitTransaction();

        List<Bank> list = mStore.getBanks();

        return banksList;
    }

    private String getGoodLong(String badLong) {
        return badLong.replace(",", ".").trim();
    }

    private Rate jsonToRateObject(JSONObject objectRate, String base, String compare) throws JSONException {
        String sellStr = getGoodLong(objectRate.getString("Sell"));
        double sellLong = Double.parseDouble(sellStr);
        String buyStr = getGoodLong(objectRate.getString("Buy"));
        double buyLong = Double.parseDouble(buyStr);

        Rate rate = new Rate();
        rate.setBuy(buyLong);
        rate.setSell(sellLong);
        rate.setBaseCurrency(base);
        rate.setCompareCurrency(compare);

        return rate;
    }

    private Observable<String> mapResponseToXML(final Response response) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {

                    String respBody = null;
                    if (response.body() != null) {
                        respBody = response.body().string();
                        response.body().close();
                    }
                    subscriber.onNext(respBody);
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    private Observable<List<Bank>> xmlResponseToListObjects(final String responseString) {
        return Observable.create(new Observable.OnSubscribe<List<Bank>>() {
            @Override
            public void call(Subscriber<? super List<Bank>> subscriber) {
                try {
                    subscriber.onNext(null);
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
