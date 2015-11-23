package apps.makarov.com.whereismycurrency.repository.protocols;

import java.util.Date;

import apps.makarov.com.whereismycurrency.repository.realm.models.RateRealm;
import io.realm.RealmObject;

/**
 * Created by makarov on 22/08/15.
 */
public interface UserHistoryProtocol<E extends RateProtocol> {

    E getRate();

    String getKey();

    void setKey(String key);

    void setRate(E rate);

    double getValue();

    void setValue(double value);

    Date getDate();

    void setDate(Date date);
}
