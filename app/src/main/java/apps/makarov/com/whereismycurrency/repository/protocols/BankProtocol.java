package apps.makarov.com.whereismycurrency.repository.protocols;

import java.util.List;

/**
 * Created by makarov on 22/08/15.
 */
public interface BankProtocol<T extends List<? extends RateProtocol>> {

    String getKey();

    void setKey(String key);

    void setRates(T rates);

    T getRates();

    String getName();

    void setName(String name);

}
