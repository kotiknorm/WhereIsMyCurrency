package apps.makarov.com.whereismycurrency.repository.models;

import apps.makarov.com.whereismycurrency.repository.protocols.CurrencyPairProtocol;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by makarov on 02/07/15.
 */

public class CurrencyPairRealm extends RealmObject implements CurrencyPairProtocol{

    @PrimaryKey
    private String key;
    private String baseCurrency;
    private String compareCurrency;

    public String getCompareCurrency() {
        return compareCurrency;
    }

    public void setCompareCurrency(String compareCurrency) {
        this.compareCurrency = compareCurrency;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
