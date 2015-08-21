package apps.makarov.com.whereismycurrency.repository;

import java.util.Date;
import java.util.List;

import apps.makarov.com.whereismycurrency.repository.models.BankRealm;
import apps.makarov.com.whereismycurrency.repository.models.CurrencyPairRealm;
import apps.makarov.com.whereismycurrency.repository.models.RateRealm;
import apps.makarov.com.whereismycurrency.repository.models.ResultOperationRealm;
import apps.makarov.com.whereismycurrency.repository.models.UserHistoryRealm;
import io.realm.RealmObject;

/**
 * Created by makarov on 27/06/15.
 */

public interface IRepository<T> {

    List<BankRealm> getBanks();

    List<RateRealm> getRates(CurrencyPairRealm currencyPair, Date date, String bankName);

    List<UserHistoryRealm> getUserHistory();

    List<ResultOperationRealm> getAllResultOperation();

    <E extends T> void saveObject(E object);

     <E extends RealmObject> void removeObject(E obj);

    void addUrlToCache(String url);

    boolean hasUrlInCache(String url);

    ResultOperationRealm getResultOperation(String key);

    ResultOperationRealm resultToHistory(ResultOperationRealm resultOperation);

    ResultOperationRealm removeResult(ResultOperationRealm resultOperation);
}

