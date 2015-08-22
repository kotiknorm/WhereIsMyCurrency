package apps.makarov.com.whereismycurrency.repository.realm.models;

import java.util.Date;

import apps.makarov.com.whereismycurrency.repository.protocols.ResultOperationProtocol;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by makarov on 01/07/15.
 */
public class ResultOperationRealm extends RealmObject
        implements ResultOperationProtocol<UserHistoryRealm, RateRealm> {

    @PrimaryKey
    private String key;
    private RateRealm exitRate;
    private UserHistoryRealm userHistory;
    private Date date;
    private boolean isHistory;

    public UserHistoryRealm getUserHistory() {
        return userHistory;
    }

    public void setUserHistory(UserHistoryRealm userHistory) {
        this.userHistory = userHistory;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public RateRealm getExitRate() {
        return exitRate;
    }

    public void setExitRate(RateRealm exitRate) {
        this.exitRate = exitRate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isHistory() {
        return isHistory;
    }

    public void setIsHistory(boolean isHistory) {
        this.isHistory = isHistory;
    }

}
