package apps.makarov.com.whereismycurrency.mappers;

import apps.makarov.com.whereismycurrency.models.CurrencyPairData;
import apps.makarov.com.whereismycurrency.repository.models.CurrencyPair;

/**
 * Created by makarov on 11/08/15.
 */
public class CurrencyPairMapper implements Mapper<CurrencyPairData, CurrencyPair> {

    @Override
    public CurrencyPairData modelToData(CurrencyPair obj) {
        return null;
    }

    @Override
    public CurrencyPair dataToModel(CurrencyPairData currencyPairData) {
        CurrencyPair pair = new CurrencyPair();
        pair.setBaseCurrency(currencyPairData.getBaseCurrency());
        pair.setCompareCurrency(currencyPairData.getCompareCurrency());
        pair.setKey(generateKey(currencyPairData));
        return pair;
    }

    public static String generateKey(CurrencyPairData pair) {
        return pair.getBaseCurrency() + "_" + pair.getCompareCurrency();
    }

}
