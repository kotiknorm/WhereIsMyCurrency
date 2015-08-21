package apps.makarov.com.whereismycurrency.repository.models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by makarov on 28/06/15.
 */

public class CacheRequestRealm extends RealmObject {

    @PrimaryKey
    private String url;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private Date date;
}
