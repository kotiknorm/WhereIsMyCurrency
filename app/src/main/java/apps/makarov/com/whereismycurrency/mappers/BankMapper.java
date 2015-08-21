package apps.makarov.com.whereismycurrency.mappers;

import apps.makarov.com.whereismycurrency.models.BankData;
import apps.makarov.com.whereismycurrency.repository.models.Bank;

/**
 * Created by makarov on 11/08/15.
 */
public class BankMapper implements Mapper<BankData,Bank> {

    @Override
    public BankData modelToData(Bank obj) {
        return null;
    }

    @Override
    public Bank dataToModel(BankData bankData) {
        Bank bank = new Bank();
        bank.setName(bankData.getName());
        bank.setKey(generateKey(bankData));
        return bank;
    }

    public static String generateKey(BankData bankData) {
        return bankData.getName();
    }
}
