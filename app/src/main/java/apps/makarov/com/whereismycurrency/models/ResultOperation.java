package apps.makarov.com.whereismycurrency.models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by makarov on 01/07/15.
 */
public class ResultOperation extends RealmObject {

    @PrimaryKey
    private String key;
    private Rate exitRate;
    private UserHistory userHistory;
    private Date date;

    public UserHistory getUserHistory() {
        return userHistory;
    }

    public void setUserHistory(UserHistory userHistory) {
        this.userHistory = userHistory;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Rate getExitRate() {
        return exitRate;
    }

    public void setExitRate(Rate exitRate) {
        this.exitRate = exitRate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public static String generateKey(ResultOperation resultOperation) {
        return resultOperation.getDate().toString();
    }

}
