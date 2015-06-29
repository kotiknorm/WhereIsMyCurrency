package apps.makarov.com.whereismycurrency.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by makarov on 26/06/15.
 */

public class Bank extends RealmObject {

    public static final String DEFAULT = "DEFAULT";
    public static final String USER_RATE = "USER_RATE";

    @PrimaryKey
    private String key;
    private String name;
    private RealmList<Rate> rates = new RealmList<>();

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setRates(RealmList<Rate> rates) {
        this.rates = rates;
    }

    public RealmList<Rate> getRates() {
        return rates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String generateKey(Bank bank) {
        return bank.getName();
    }


}
