package apps.makarov.com.whereismycurrency.net.requests;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import apps.makarov.com.whereismycurrency.Utils.StringUtils;
import apps.makarov.com.whereismycurrency.models.Bank;
import apps.makarov.com.whereismycurrency.models.Rate;
import io.realm.RealmObject;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by makarov on 28/06/15.
 */
public class BankRequest extends WimcRequest {

    public static final String BANK_RATES_URL = "http://informer.kovalut.ru/webmaster/xml-table.php?kod=7701";

    @Override
    public String getPath() {
        return BANK_RATES_URL;
    }

    @Override
    public Observable<List<? extends RealmObject>> observableJsonToStatusCode(final JSONObject jsonObject) {
        return Observable.create(new Observable.OnSubscribe<List<? extends RealmObject>>() {
            @Override
            public void call(Subscriber<? super List<? extends RealmObject>> subscriber) {
                try {
                    jsonToListBank(jsonObject);
                    subscriber.onNext(jsonToListBank(jsonObject));
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    private List<Bank> jsonToListBank(JSONObject jsonObj) throws XmlPullParserException, IOException, JSONException {

        JSONArray actualRates = jsonObj.getJSONObject("Exchange_Rates").getJSONObject("Actual_Rates").getJSONArray("Bank");
        Log.d("JSON", actualRates.toString());
        List<Bank> list = new ArrayList<>();

        for (int i = 0; i < actualRates.length(); i++) {
            Bank bank = jsonToBank(actualRates.getJSONObject(i));
            list.add(bank);
        }
        return list;
    }

    private Bank jsonToBank(JSONObject jsonObj) throws JSONException {
        Bank bank = new Bank();

        String nameBank = jsonObj.getString("Name");
        String changeTime = jsonObj.getString("ChangeTime");
        bank.setName(nameBank);
        bank.setChangeRate(new Date());

        JSONObject objectEur = jsonObj.getJSONObject("EUR");
        Rate eurRate = jsonToRate(nameBank, objectEur, "RUB", "EUR");
        bank.getRates().add(eurRate);

        JSONObject objectUsd = jsonObj.getJSONObject("USD");
        Rate usdRate = jsonToRate(nameBank, objectUsd, "RUB", "USD");
        bank.getRates().add(usdRate);

        bank.setKey(Bank.generateKey(bank));
        return bank;
    }

    private Rate jsonToRate(String bankName, JSONObject objectRate, String base, String compare) throws JSONException {
        String sellStr = StringUtils.getGoodLong(objectRate.getString("Sell"));
        double sellLong = Double.parseDouble(sellStr);
        String buyStr = StringUtils.getGoodLong(objectRate.getString("Buy"));
        double buyLong = Double.parseDouble(buyStr);

        Rate rate = new Rate();
        rate.setBuy(buyLong);
        rate.setSell(sellLong);
        rate.setBaseCurrency(base);
        rate.setCompareCurrency(compare);
        rate.setKey(Rate.generateKey(bankName, rate));

        return rate;
    }

}
