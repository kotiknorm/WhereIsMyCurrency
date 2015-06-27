package apps.makarov.com.whereismycurrency.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by makarov on 26/06/15.
 */

public class Rate extends RealmObject {

    @PrimaryKey
    private String key;
    private double buy;
    private double sell;
    private String baseCurrency;
    private String compareCurrency;

    public void setBuy(double buy) {
        this.buy = buy;
    }

    public void setSell(double sell) {
        this.sell = sell;
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

    public double getSell() {
        return sell;
    }

    public double getBuy() {
        return buy;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public static String generateKey(String bankName, Rate rate){
        return rate.getBaseCurrency() + "_" + rate.getCompareCurrency() + "_" + bankName;
    }

}
