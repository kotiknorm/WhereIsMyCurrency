package apps.makarov.com.whereismycurrency.repository.realm.models;

import java.util.Date;

import apps.makarov.com.whereismycurrency.repository.protocols.UserHistoryProtocol;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by makarov on 28/06/15.
 */
public class UserHistoryRealm extends RealmObject implements UserHistoryProtocol {

    @PrimaryKey
    private String key;
    private RateRealm rate;
    private double value;
    private Date date;

    public RateRealm getRate() {
        return rate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setRate(RateRealm rate) {
        this.rate = rate;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
