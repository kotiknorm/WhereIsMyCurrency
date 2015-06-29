package apps.makarov.com.whereismycurrency.models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by makarov on 28/06/15.
 */
public class UserHistory extends RealmObject {

    @PrimaryKey
    private String key;
    private Rate rate;
    private double value;
    private Date date;

    public Rate getRate() {
        return rate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setRate(Rate rate) {
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

    public static String generateKey(UserHistory userHistory) {
        return userHistory.getDate().toString();
    }


}
