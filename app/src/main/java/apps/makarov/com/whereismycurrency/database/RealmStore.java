package apps.makarov.com.whereismycurrency.database;

import android.app.Application;

import java.util.Date;
import java.util.List;

import apps.makarov.com.whereismycurrency.models.Bank;
import apps.makarov.com.whereismycurrency.models.CacheRequest;
import apps.makarov.com.whereismycurrency.models.Rate;
import apps.makarov.com.whereismycurrency.models.UserHistory;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;

/**
 * Created by makarov on 27/06/15.
 */

public class RealmStore implements IStore<RealmObject> {

    private final Application application;

    public RealmStore(Application application) {
        this.application = application;
    }

    @Override
    public List<Bank> getBanks() {
        List<Bank> list = Realm.getInstance(application).where(Bank.class).findAll();
        Realm.getInstance(application).close();
        return list;
    }

    @Override
    public List<Rate> getRates(String baseCurrency, String compareCurrency, Date date, String bankName) {

        RealmQuery<Rate> query = Realm.getInstance(application).where(Rate.class)
                .equalTo("baseCurrency", baseCurrency).equalTo("compareCurrency", compareCurrency).notEqualTo("bank", Bank.USER_RATE);

        if (bankName != Bank.DEFAULT)
            query = query.equalTo("bank", bankName);

        return query.findAll();
    }

    @Override
    public List<UserHistory> getUserHistory() {
        return Realm.getInstance(application).where(UserHistory.class).findAll();
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
        beginTransaction();
        Realm.getInstance(application).copyToRealmOrUpdate(obj);
        commitTransaction();
    }

    @Override
    public void addUrlToCache(String url) {
        CacheRequest cacheRequest = new CacheRequest();
        cacheRequest.setUrl(url);
        cacheRequest.setDate(new Date());


        beginTransaction();
        Realm.getInstance(application).copyToRealmOrUpdate(cacheRequest);
        commitTransaction();
    }

    @Override
    public boolean isUrlInCache(String url) {
        return Realm.getInstance(application).where(CacheRequest.class).equalTo("url", url).findFirst() != null;
    }

}
