package apps.makarov.com.whereismycurrency.database;

import java.util.Date;
import java.util.List;

import apps.makarov.com.whereismycurrency.models.Bank;
import apps.makarov.com.whereismycurrency.models.Rate;
import apps.makarov.com.whereismycurrency.models.UserHistory;

/**
 * Created by makarov on 27/06/15.
 */

public interface IStore<T> {

    List<Bank> getBanks();

    List<Rate> getRates(String baseCurrency, String compareCurrency, Date date, String bankName);

    List<UserHistory> getUserHistory();

    void beginTransaction();

    void commitTransaction();

    <E extends T> void saveObject(E object);

    List<Rate> getAllCurrencyWithBase(String baseCurrency);

    void addUrlToCache(String url);

    boolean isUrlInCache(String url);
}

