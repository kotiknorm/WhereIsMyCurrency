package apps.makarov.com.whereismycurrency.mappers;

import apps.makarov.com.whereismycurrency.models.RateData;
import apps.makarov.com.whereismycurrency.repository.models.Rate;

/**
 * Created by makarov on 11/08/15.
 */
public class RateMapper implements Mapper<RateData, Rate> {

    private CurrencyPairMapper mapper = new CurrencyPairMapper();

    @Override
    public RateData modelToData(Rate obj) {
        return null;
    }

    @Override
    public Rate dataToModel(RateData rateData) {
        Rate rate = new Rate();
        rate.setBank(rateData.getBank());
        rate.setValue(rateData.getValue());
        rate.setChangeRate(rateData.getChangeRate());
        rate.setKey(generateKey(rateData));
        rate.setCurrencyPair(mapper.dataToModel(rateData.getCurrencyPair()));

        return rate;
    }

    public static String generateKey(RateData rate) {
        return rate.getCurrencyPair().getBaseCurrency() + "_" + rate.getCurrencyPair().getCompareCurrency() + "_" + rate.getBank() + "_" + rate.getValue();
    }

}
