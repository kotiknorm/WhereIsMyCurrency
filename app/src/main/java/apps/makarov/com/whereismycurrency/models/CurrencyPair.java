package apps.makarov.com.whereismycurrency.models;

/**
 * Created by makarov on 02/07/15.
 */

public class CurrencyPair {

    private String baseCurrency;
    private String compareCurrency;

    public CurrencyPair(){
    }

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


    public static CurrencyPair createPair(String baseCurrency, String compareCurrency){
        CurrencyPair pair = new CurrencyPair();
        pair.setBaseCurrency(baseCurrency);
        pair.setCompareCurrency(compareCurrency);
        return pair;
    }


}
