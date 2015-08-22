package apps.makarov.com.whereismycurrency.repository.realm.models;


import java.util.Date;

import apps.makarov.com.whereismycurrency.DateUtils;
import apps.makarov.com.whereismycurrency.models.Bank;
import apps.makarov.com.whereismycurrency.repository.protocols.RateProtocol;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by makarov on 26/06/15.
 */

public class RateRealm extends RealmObject implements RateProtocol<CurrencyPairRealm> {

    @PrimaryKey
    private String key;
    private double value;
    private CurrencyPairRealm currencyPair;
    private Date changeRate = DateUtils.getTodayDate();
    private String bank = Bank.DEFAULT; // bank PK

    public CurrencyPairRealm getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(CurrencyPairRealm currencyPair) {
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
