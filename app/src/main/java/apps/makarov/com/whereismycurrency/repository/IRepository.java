package apps.makarov.com.whereismycurrency.repository;

import java.util.Date;
import java.util.List;

import apps.makarov.com.whereismycurrency.models.CurrencyPair;
import apps.makarov.com.whereismycurrency.models.Bank;
import apps.makarov.com.whereismycurrency.models.Rate;
import apps.makarov.com.whereismycurrency.models.ResultOperation;
import apps.makarov.com.whereismycurrency.models.UserHistory;
import io.realm.RealmObject;

/**
 * Created by makarov on 27/06/15.
 */

public interface IRepository<T> {

    List<Bank> getBanks();

    List<Rate> getRates(CurrencyPair currencyPair, Date date, String bankName);

    List<UserHistory> getUserHistory();

    List<ResultOperation> getAllResultOperation();

    <E extends T> void saveObject(E object);

     <E extends RealmObject> void removeObject(E obj);

    void addUrlToCache(String url);

    boolean hasUrlInCache(String url);

    ResultOperation getResultOperation(String key);

    ResultOperation resultToHistory(ResultOperation resultOperation);

    ResultOperation removeResult(ResultOperation resultOperation);
}

