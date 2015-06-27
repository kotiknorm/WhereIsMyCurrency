package apps.makarov.com.whereismycurrency.models;

import io.realm.RealmObject;

/**
 * Created by makarov on 26/06/15.
 */

public class Rate extends RealmObject {

    public void setBuy(double buy) {
        this.buy = buy;
    }

    public void setSell(double sell) {
        this.sell = sell;
    }

    private double buy;

    private double sell;

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

    private String baseCurrency;

    private String compareCurrency;

//    public Rate(double buy, double sell, String baseCurrency, String compareCurrency ){
//        this.buy = buy;
//        this.sell = sell;
//        this.baseCurrency = baseCurrency;
//        this.compareCurrency = compareCurrency;
//    }

    public double getSell() {
        return sell;
    }

    public double getBuy() {
        return buy;
    }



}
