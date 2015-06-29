package apps.makarov.com.whereismycurrency.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by makarov on 26/06/15.
 */

public class Rate extends RealmObject {

    public static List<String> codes = new ArrayList<>();

    public static final String RUB_CODE = "RUB";
    public static final String EUR_CODE = "EUR";
    public static final String USD_CODE = "USD";

    static {
        codes.add(RUB_CODE);
        codes.add(EUR_CODE);
        codes.add(USD_CODE);
    }


    @PrimaryKey
    private String key;
    private double value;
    private String baseCurrency;
    private String compareCurrency;
    private Date changeRate;
    private String bank; // bank PK

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

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
