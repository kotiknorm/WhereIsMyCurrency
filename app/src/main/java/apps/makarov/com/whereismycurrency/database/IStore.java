package apps.makarov.com.whereismycurrency.database;

import java.util.Date;
import java.util.List;

import apps.makarov.com.whereismycurrency.models.Bank;
import apps.makarov.com.whereismycurrency.models.Rate;
import apps.makarov.com.whereismycurrency.models.ResultOperation;
import apps.makarov.com.whereismycurrency.models.UserHistory;

/**
 * Created by makarov on 27/06/15.
 */

public interface IStore<T> {

    List<Bank> getBanks();

    List<Rate> getRates(String baseCurrency, String compareCurrency, Date date, String bankName);

    List<UserHistory> getUserHistory();

    <E extends T> void saveObject(E object);

    void addUrlToCache(String url);

    boolean hasUrlInCache(String url);

    ResultOperation getResultOperation(String key);
}

