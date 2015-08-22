package apps.makarov.com.whereismycurrency.mappers.realm;

import apps.makarov.com.whereismycurrency.mappers.Mapper;
import apps.makarov.com.whereismycurrency.models.Rate;
import apps.makarov.com.whereismycurrency.repository.realm.models.RateRealm;
import apps.makarov.com.whereismycurrency.repository.protocols.RateProtocol;

/**
 * Created by makarov on 11/08/15.
 */
public class RateRealmMapper implements Mapper<Rate, RateProtocol> {

    private CurrencyPairRealmMapper mapper = new CurrencyPairRealmMapper();

    @Override
    public Rate modelToData(RateProtocol obj) {
        return null;
    }

    @Override
    public RateRealm dataToModel(Rate rateData) {
        RateRealm rate = new RateRealm();
        rate.setBank(rateData.getBank());
        rate.setValue(rateData.getValue());
        rate.setChangeRate(rateData.getChangeRate());
        rate.setKey(generateKey(rateData));
        rate.setCurrencyPair(mapper.dataToModel(rateData.getCurrencyPair()));

        return rate;
    }

    public static String generateKey(Rate rate) {
        return rate.getCurrencyPair().getBaseCurrency() + "_" + rate.getCurrencyPair().getCompareCurrency() + "_" + rate.getBank() + "_" + rate.getValue();
    }

}
