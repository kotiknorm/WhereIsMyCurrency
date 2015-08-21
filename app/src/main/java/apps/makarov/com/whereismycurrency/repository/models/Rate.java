package apps.makarov.com.whereismycurrency.repository.models;


import java.util.Date;

import apps.makarov.com.whereismycurrency.DateUtils;
import apps.makarov.com.whereismycurrency.models.BankData;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by makarov on 26/06/15.
 */

public class Rate extends RealmObject {

    @PrimaryKey
    private String key;  // composite key (generateKey method)
    private double value;
    private CurrencyPair currencyPair;
    private Date changeRate = DateUtils.getTodayDate(); // today
    private String bank = BankData.DEFAULT; // bank PK

    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(CurrencyPair currencyPair) {
        this.currencyPair = currencyPair;
    }

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

    public double getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
