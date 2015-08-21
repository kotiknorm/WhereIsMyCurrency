package apps.makarov.com.whereismycurrency.mappers.realm;

import apps.makarov.com.whereismycurrency.mappers.Mapper;
import apps.makarov.com.whereismycurrency.models.CurrencyPair;
import apps.makarov.com.whereismycurrency.repository.models.CurrencyPairRealm;

/**
 * Created by makarov on 11/08/15.
 */
public class CurrencyPairRealmMapper implements Mapper<CurrencyPair, CurrencyPairRealm> {

    @Override
    public CurrencyPair modelToData(CurrencyPairRealm obj) {
        return null;
    }

    @Override
    public CurrencyPairRealm dataToModel(CurrencyPair currencyPair) {
        CurrencyPairRealm pair = new CurrencyPairRealm();
        pair.setBaseCurrency(currencyPair.getBaseCurrency());
        pair.setCompareCurrency(currencyPair.getCompareCurrency());
        pair.setKey(generateKey(currencyPair));
        return pair;
    }

    public static String generateKey(CurrencyPair pair) {
        return pair.getBaseCurrency() + "_" + pair.getCompareCurrency();
    }

}
