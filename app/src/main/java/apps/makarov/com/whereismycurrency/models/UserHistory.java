package apps.makarov.com.whereismycurrency.models;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by makarov on 28/06/15.
 */
public class UserHistory extends RealmObject {

    private Rate rate;
    private double value;
    private Date date;

    public Rate getRate() {
        return rate;
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


}
