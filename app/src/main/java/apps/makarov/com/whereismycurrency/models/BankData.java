package apps.makarov.com.whereismycurrency.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by makarov on 26/06/15.
 */

public class BankData{

    public static final String DEFAULT = "DEFAULT";
    public static final String USER_RATE = "USER_RATE";  // rates from user history

    private String name;
    private List<RateData> rates = new ArrayList<>();

    public void setRates(List<RateData> rates) {
        this.rates = rates;
    }

    public List<RateData> getRates() {
        return rates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
