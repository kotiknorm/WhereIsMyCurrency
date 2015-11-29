package apps.makarov.com.whereismycurrency.repository.realm;

import android.app.Application;

import java.util.Date;
import java.util.List;

import apps.makarov.com.whereismycurrency.models.Bank;
import apps.makarov.com.whereismycurrency.repository.IRepository;
import apps.makarov.com.whereismycurrency.repository.realm.models.BankRealm;
import apps.makarov.com.whereismycurrency.repository.realm.models.CacheRequestRealm;
import apps.makarov.com.whereismycurrency.repository.realm.models.CurrencyPairRealm;
import apps.makarov.com.whereismycurrency.repository.realm.models.RateRealm;
import apps.makarov.com.whereismycurrency.repository.realm.models.ResultOperationRealm;
import apps.makarov.com.whereismycurrency.repository.realm.models.UserHistoryRealm;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by makarov on 27/06/15.
 */

public class RealmRepository implements IRepository<RealmObject, ResultOperationRealm, CurrencyPairRealm> {

    private final Application application;

    public RealmRepository(Application application) {
        this.application = application;
    }

    @Override
    public List<BankRealm> getBanks() {
        List<BankRealm> list = Realm.getInstance(application).where(BankRealm.class).findAll();
        Realm.getInstance(application).close();
        return list;
    }

    @Override
    public List<RateRealm> getRates(CurrencyPairRealm pair, Date date, String bankName) {

        RealmQuery<RateRealm> query = Realm.getInstance(application)
                .where(RateRealm.class).equalTo("currencyPair.key", pair.getKey())
                .notEqualTo("bank", Bank.USER_RATE)
                ;
        // TODO Fixed date (temp)
                //.equalTo("changeRate", date)
        if (bankName != Bank.DEFAULT)
            query = query.equalTo("bank", bankName);

        return query.findAll();
    }

    @Override
    public List<RateRealm> getRatesByCurrencyPair(CurrencyPairRealm pair, Date date) {

        RealmQuery<RateRealm> query = Realm.getInstance(application)
                .where(RateRealm.class).equalTo("currencyPair.key", pair.getKey())
                .notEqualTo("bank", Bank.USER_RATE).notEqualTo("bank", Bank.DEFAULT);

        // TODO Fixed date (temp)
                //.equalTo("changeRate", date)
        return query.findAll();
    }

    @Override
    public List<UserHistoryRealm> getUserHistory() {
        return Realm.getInstance(application).where(UserHistoryRealm.class).findAllSorted("date", false);
    }

    @Override
    public List<ResultOperationRealm> getAllResultOperation() {

        RealmResults<ResultOperationRealm> result = Realm.getInstance(application).where(ResultOperationRealm.class)
                .findAllSorted("date", false);

        result.sort("isHistory", true);
        Realm.getInstance(application).close();

        return result;
    }

    @Override
    public void saveObject(RealmObject obj) {
        beginTransaction();
        Realm.getInstance(application).copyToRealmOrUpdate(obj);
        commitTransaction();
    }

    @Override
    public void removeObject(RealmObject obj) {
        beginTransaction();
        obj.removeFromRealm();
        commitTransaction();
    }

    @Override
    public void addUrlToCache(String url) {
        CacheRequestRealm cacheRequest = new CacheRequestRealm();
        cacheRequest.setUrl(url);
        cacheRequest.setDate(new Date());

        beginTransaction();
        Realm.getInstance(application).copyToRealmOrUpdate(cacheRequest);
        commitTransaction();
    }

    @Override
    public boolean hasUrlInCache(String url) {
        return Realm.getInstance(application).where(CacheRequestRealm.class).equalTo("url", url).findFirst() != null;
    }

    @Override
    public ResultOperationRealm getResultOperation(String key) {
        return Realm.getInstance(application).where(ResultOperationRealm.class).equalTo("key", key).findFirst();
    }

    @Override
    public ResultOperationRealm resultToHistory(ResultOperationRealm resultOperation) {
        beginTransaction();
        resultOperation.setIsHistory(true);

        Realm.getInstance(application).copyToRealmOrUpdate(resultOperation);

        commitTransaction();
        return resultOperation;
    }

    @Override
    public ResultOperationRealm removeResult(ResultOperationRealm resultOperation) {
        removeObject(getResultOperation(resultOperation.getKey()));
        return resultOperation;
    }

    private void beginTransaction() {
        Realm.getInstance(application).beginTransaction();
    }

    private void commitTransaction() {
        Realm.getInstance(application).commitTransaction();
    }

}
