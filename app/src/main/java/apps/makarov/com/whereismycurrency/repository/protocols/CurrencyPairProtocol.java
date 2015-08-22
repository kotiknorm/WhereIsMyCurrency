package apps.makarov.com.whereismycurrency.repository.protocols;

import apps.makarov.com.whereismycurrency.models.UniqueModel;

/**
 * Created by makarov on 22/08/15.
 */
public interface CurrencyPairProtocol extends UniqueModel {

    String getCompareCurrency();

    void setCompareCurrency(String compareCurrency);

    String getBaseCurrency() ;

    void setBaseCurrency(String baseCurrency) ;
}
