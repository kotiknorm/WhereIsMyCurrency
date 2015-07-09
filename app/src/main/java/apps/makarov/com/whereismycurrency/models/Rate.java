package apps.makarov.com.whereismycurrency.models;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import apps.makarov.com.whereismycurrency.DateUtils;
import apps.makarov.com.whereismycurrency.R;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by makarov on 26/06/15.
 */

public class Rate extends RealmObject {

    private static List<String> codes = new ArrayList<>();
    private static List<CurrencyPair> listPairCodes = new ArrayList<>();

    public static final String RUB_CODE = "RUB";
    public static final String EUR_CODE = "EUR";
    public static final String USD_CODE = "USD";

    static {
        codes.add(RUB_CODE);
        codes.add(EUR_CODE);
        codes.add(USD_CODE);

        for(String baseCodes : codes){
            for(String compareCodes : codes){
                if(!baseCodes.equals(compareCodes)){
                    CurrencyPair pair =  CurrencyPair.createPair(baseCodes, compareCodes);
                    listPairCodes.add(pair);
                }
            }
        }
    }

    public static List<String> getCodesList(){
        return Collections.unmodifiableList(codes);
    }

    public static List<CurrencyPair> getPairCodesList(){
        return Collections.unmodifiableList(listPairCodes);
    }

    @PrimaryKey
    private String key;  // composite key (generateKey method)
    private double value;
    private CurrencyPair currencyPair;
    private Date changeRate = DateUtils.getTodayDate(); // today
    private String bank = Bank.DEFAULT; // bank PK

    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(CurrencyPair currencyPair) {
        this.currencyPair = currencyPair;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public Date getChangeRate() {
        return changeRate;
    }

    public void setChangeRate(Date changeRate) {
        this.changeRate = changeRate;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public static String generateKey(Rate rate) {
        return rate.getCurrencyPair().getBaseCurrency() + "_" + rate.getCurrencyPair().getCompareCurrency() + "_" + rate.getBank() + "_" + rate.getValue();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Drawable getCurrencyIcon(Context context, String currency) {
        switch (currency) {
            case EUR_CODE:
                return context.getDrawable(R.drawable.eur);
            case RUB_CODE:
                return context.getDrawable(R.drawable.rub);
            case USD_CODE:
                return context.getDrawable(R.drawable.usd);
            default:
                return context.getDrawable(R.drawable.eur);
        }
    }

}
