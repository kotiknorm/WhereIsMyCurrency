package apps.makarov.com.whereismycurrency.models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by makarov on 26/06/15.
 */

public class Rate extends RealmObject {

    @PrimaryKey
    private String key;
    private double value;
    private String baseCurrency;
    private String compareCurrency;
    private Date changeRate;

    public Date getChangeRate() {
        return changeRate;
    }

    public void setChangeRate(Date changeRate) {
        this.changeRate = changeRate;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public String getCompareCurrency() {
        return compareCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public void setCompareCurrency(String compareCurrency) {
        this.compareCurrency = compareCurrency;
    }

    public double getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public static String generateKey(String bankName, Rate rate) {
        return rate.getBaseCurrency() + "_" + rate.getCompareCurrency() + "_" + bankName;
    }

}
