package apps.makarov.com.whereismycurrency.mappers.realm;

import apps.makarov.com.whereismycurrency.mappers.Mapper;
import apps.makarov.com.whereismycurrency.models.Bank;
import apps.makarov.com.whereismycurrency.repository.realm.models.BankRealm;
import apps.makarov.com.whereismycurrency.repository.protocols.BankProtocol;

/**
 * Created by makarov on 11/08/15.
 */
public class BankRealmMapper implements Mapper<Bank, BankRealm> {

    @Override
    public Bank dataToModel(BankRealm bankData) {
        Bank bank = new Bank();
        bank.setName(bankData.getName());
        //bank.setKey(generateKey(bankData));
        return bank;
    }

    @Override
    public BankRealm modelToData(Bank bankModel) {
        BankRealm bank = new BankRealm();
        bank.setName(bankModel.getName());
        bank.setKey(generateKey(bankModel));
        return bank;
    }

    public static String generateKey(Bank bank) {
        return bank.getName();
    }
}
