package apps.makarov.com.whereismycurrency.repository.protocols;

import java.util.Date;

/**
 * Created by makarov on 22/08/15.
 */
public interface RateProtocol<T extends CurrencyPairProtocol> {

    CurrencyPairProtocol getCurrencyPair();

    void setCurrencyPair(T currencyPair);

    String getBank();

    void setBank(String bank);

    Date getChangeRate();

    void setChangeRate(Date changeRate);

    void setValue(double value);

    double getValue();

    String getKey();

    void setKey(String key);
}
