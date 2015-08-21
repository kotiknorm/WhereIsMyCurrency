package apps.makarov.com.whereismycurrency.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by makarov on 26/06/15.
 */

public class Bank {

    public static final String DEFAULT = "DEFAULT";
    public static final String USER_RATE = "USER_RATE";  // rates from user history

    private String name;
    private List<Rate> rates = new ArrayList<>();

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
