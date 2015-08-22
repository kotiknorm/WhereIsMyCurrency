package apps.makarov.com.whereismycurrency.repository;

import java.util.Date;
import java.util.List;

import apps.makarov.com.whereismycurrency.repository.protocols.BankProtocol;
import apps.makarov.com.whereismycurrency.repository.protocols.CurrencyPairProtocol;
import apps.makarov.com.whereismycurrency.repository.protocols.RateProtocol;
import apps.makarov.com.whereismycurrency.repository.protocols.ResultOperationProtocol;
import apps.makarov.com.whereismycurrency.repository.protocols.UserHistoryProtocol;
import io.realm.RealmObject;

/**
 * Created by makarov on 27/06/15.
 */

public interface IRepository<T> {

    List<? extends BankProtocol> getBanks();

    <E extends CurrencyPairProtocol> List<? extends RateProtocol> getRates(E currencyPair, Date date, String bankName);

    List<? extends UserHistoryProtocol> getUserHistory();

    List<? extends ResultOperationProtocol> getAllResultOperation();

    <E extends T> void saveObject(E object);

     <E extends RealmObject> void removeObject(E obj);

    void addUrlToCache(String url);

    boolean hasUrlInCache(String url);

    <E extends ResultOperationProtocol>E getResultOperation(String key);

    <E extends ResultOperationProtocol>E resultToHistory(E resultOperation);

    <E extends ResultOperationProtocol>E removeResult(E resultOperation);
}

