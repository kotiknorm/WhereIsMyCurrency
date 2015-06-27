package apps.makarov.com.whereismycurrency.database;

import java.util.List;

import apps.makarov.com.whereismycurrency.models.Bank;
import apps.makarov.com.whereismycurrency.models.Rate;
import io.realm.RealmObject;

/**
 * Created by makarov on 27/06/15.
 */

public interface IStore {

    List<Bank> getBanks();

    List<Rate> getRates(String bankName);

    void beginTransaction();

    void commitTransaction();

    <E extends RealmObject>void saveObject(E obj);
}
