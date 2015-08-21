package apps.makarov.com.whereismycurrency.mappers.realm;

import apps.makarov.com.whereismycurrency.mappers.Mapper;
import apps.makarov.com.whereismycurrency.models.Bank;
import apps.makarov.com.whereismycurrency.repository.models.BankRealm;

/**
 * Created by makarov on 11/08/15.
 */
public class BankRealmMapper implements Mapper<Bank, BankRealm> {

    @Override
    public Bank modelToData(apps.makarov.com.whereismycurrency.repository.models.BankRealm obj) {
        return null;
    }

    @Override
    public BankRealm dataToModel(Bank bankData) {
        BankRealm bank = new BankRealm();
        bank.setName(bankData.getName());
        bank.setKey(generateKey(bankData));
        return bank;
    }

    public static String generateKey(Bank bank) {
        return bank.getName();
    }
}
