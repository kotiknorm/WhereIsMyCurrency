package apps.makarov.com.whereismycurrency.models;

import java.util.Date;

/**
 * Created by makarov on 28/06/15.
 */
public class UserHistoryData  {

    private RateData rate;
    private double value;
    private Date date;

    public RateData getRate() {
        return rate;
    }

    public void setRate(RateData rate) {
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
