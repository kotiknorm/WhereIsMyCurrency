package apps.makarov.com.whereismycurrency.mappers.realm;

import apps.makarov.com.whereismycurrency.mappers.Mapper;
import apps.makarov.com.whereismycurrency.models.Bank;
import apps.makarov.com.whereismycurrency.repository.models.BankRealm;
import apps.makarov.com.whereismycurrency.repository.protocols.BankProtocol;

/**
 * Created by makarov on 11/08/15.
 */
public class BankRealmMapper implements Mapper<Bank, BankProtocol> {

    @Override
    public Bank modelToData(BankProtocol obj) {
        return null;
    }

    @Override
    public BankProtocol dataToModel(Bank bankData) {
        BankRealm bank = new BankRealm();
        bank.setName(bankData.getName());
        bank.setKey(generateKey(bankData));
        return bank;
    }

    public static String generateKey(Bank bank) {
        return bank.getName();
    }
}
