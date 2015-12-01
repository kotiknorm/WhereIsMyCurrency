package apps.makarov.com.whereismycurrency.mappers.realm;

import java.util.ArrayList;
import java.util.List;

import apps.makarov.com.whereismycurrency.mappers.Mapper;
import apps.makarov.com.whereismycurrency.models.Bank;
import apps.makarov.com.whereismycurrency.models.Rate;
import apps.makarov.com.whereismycurrency.repository.realm.models.BankRealm;
import apps.makarov.com.whereismycurrency.repository.protocols.BankProtocol;
import apps.makarov.com.whereismycurrency.repository.realm.models.RateRealm;
import io.realm.RealmList;

/**
 * Created by makarov on 11/08/15.
 */
public class BankRealmMapper implements Mapper<Bank, BankProtocol> {

    private RateRealmMapper rateRealmMapper = new RateRealmMapper();

    @Override
    public Bank dataToModel(BankProtocol bankData) {
        Bank bank = new Bank();
        bank.setName(bankData.getName());

        List<Rate> list = new ArrayList<>();
        for (int i = 0; i < bankData.getRates().size(); i++) {
            list.add(rateRealmMapper.dataToModel((RateRealm)bankData.getRates().get(i)));
        }
        bank.setRates(list);
        return bank;
    }

    @Override
    public BankRealm modelToData(Bank bankModel) {
        BankRealm bank = new BankRealm();
        bank.setName(bankModel.getName());
        bank.setKey(generateKey(bankModel));

        RealmList<RateRealm> list = new RealmList<>();
        for (int i = 0; i < bankModel.getRates().size(); i++) {
            list.add(rateRealmMapper.modelToData(bankModel.getRates().get(i)));
        }
        bank.setRates(list);
        return bank;
    }

    public static String generateKey(Bank bank) {
        return bank.getName();
    }
}
