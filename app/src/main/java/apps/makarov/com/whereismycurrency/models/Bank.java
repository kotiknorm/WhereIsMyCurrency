package apps.makarov.com.whereismycurrency.models;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by makarov on 26/06/15.
 */

public class Bank extends RealmObject {

    public void setRates(RealmList<Rate> rates) {
        this.rates = rates;
    }

    private RealmList<Rate> rates = new RealmList<>();

    @PrimaryKey
    private String name;

    public RealmList<Rate> getRates() {
        return rates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getChangeRate() {
        return changeRate;
    }

    public void setChangeRate(Date changeRate) {
        this.changeRate = changeRate;
    }

    private Date changeRate;


//    public Bank(String name, List<Rate> bankRates, Date changeRate){
//        setRates(bankRates);
//        this.name = name;
//        this.changeRate = changeRate;
//    }

}
