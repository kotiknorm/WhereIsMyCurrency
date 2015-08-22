package apps.makarov.com.whereismycurrency.repository.protocols;

import java.util.Date;

import apps.makarov.com.whereismycurrency.repository.models.CurrencyPairRealm;

/**
 * Created by makarov on 22/08/15.
 */
public interface RateProtocol {

    CurrencyPairRealm getCurrencyPair();

    void setCurrencyPair(CurrencyPairProtocol currencyPair);

    String getBank();

    void setBank(String bank);

    Date getChangeRate();

    void setChangeRate(Date changeRate);

    void setValue(double value);

    double getValue();

    String getKey();

    void setKey(String key);
}
