package apps.makarov.com.whereismycurrency.repository.models;

import apps.makarov.com.whereismycurrency.repository.protocols.BankProtocol;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by makarov on 26/06/15.
 */

public class BankRealm extends RealmObject implements BankProtocol<RealmList<RateRealm>> {

    @PrimaryKey
    private String key;
    private String name;
    private RealmList<RateRealm> rates = new RealmList<RateRealm>();

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setRates(RealmList rates) {
        this.rates = rates;
    }

    public RealmList getRates() {
        return rates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
