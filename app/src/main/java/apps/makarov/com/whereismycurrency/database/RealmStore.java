package apps.makarov.com.whereismycurrency.database;

import android.app.Application;

import java.util.List;

import apps.makarov.com.whereismycurrency.models.Bank;
import apps.makarov.com.whereismycurrency.models.Rate;
import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by makarov on 27/06/15.
 */
public class RealmStore implements IStore {

    private final Application application;

    public RealmStore(Application application){
        this.application = application;
    }

    @Override
    public List<Bank> getBanks() {
        return Realm.getInstance(application).allObjects(Bank.class);
    }

    @Override
    public List<Rate> getRates(String bankName) {
        return null;
    }

    @Override
    public void beginTransaction() {
        Realm.getInstance(application).beginTransaction();
    }

    @Override
    public void commitTransaction() {
        Realm.getInstance(application).commitTransaction();
    }

    @Override
    public <E extends RealmObject> void saveObject(E obj) {
        Realm.getInstance(application).copyToRealmOrUpdate(obj);
    }

}
