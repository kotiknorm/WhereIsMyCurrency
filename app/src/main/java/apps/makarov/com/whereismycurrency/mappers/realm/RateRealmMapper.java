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
    public Rate dataToModel(RateProtocol rateData) {
        Rate rate = new Rate();
        rate.setBank(rateData.getBank());
        rate.setValue(rateData.getValue());
        rate.setChangeRate(rateData.getChangeRate());
        rate.setCurrencyPair(mapper.dataToModel(rateData.getCurrencyPair()));

        return rate;
    }

    @Override
    public RateRealm modelToData(Rate rateModel) {
        RateRealm rate = new RateRealm();
        rate.setBank(rateModel.getBank());
        rate.setValue(rateModel.getValue());
        rate.setChangeRate(rateModel.getChangeRate());
        rate.setKey(generateKey(rateModel));
        rate.setCurrencyPair(mapper.modelToData(rateModel.getCurrencyPair()));

        return rate;
    }

    public static String generateKey(Rate rate) {
        return rate.getCurrencyPair().getBaseCurrency() + "_" + rate.getCurrencyPair().getCompareCurrency() + "_" + rate.getBank() + "_" + rate.getValue();
    }

}
