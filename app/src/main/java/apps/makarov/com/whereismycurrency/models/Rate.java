package apps.makarov.com.whereismycurrency.models;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import apps.makarov.com.whereismycurrency.DateUtils;
import apps.makarov.com.whereismycurrency.R;
import apps.makarov.com.whereismycurrency.view.base.BaseContextView;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by makarov on 26/06/15.
 */

public class Rate extends RealmObject {

    public static List<String> codes = new ArrayList<>();

    public static final String RUB_CODE = "RUB";
    public static final String EUR_CODE = "EUR";
    public static final String USD_CODE = "USD";

    static {
        codes.add(RUB_CODE);
        codes.add(EUR_CODE);
        codes.add(USD_CODE);
    }

    @PrimaryKey
    private String key;  // composite key (generateKey method)
    private double value;
    private String baseCurrency;
    private String compareCurrency;
    private Date changeRate = DateUtils.getTodayDate(); // today
    private String bank = Bank.DEFAULT; // bank PK

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

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public String getCompareCurrency() {
        return compareCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public void setCompareCurrency(String compareCurrency) {
        this.compareCurrency = compareCurrency;
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
        return rate.getBaseCurrency() + "_" + rate.getCompareCurrency() + "_" + rate.getBank() + "_" + rate.getValue();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Drawable getRateIcon(BaseContextView contextView, String currency) {
        switch (currency) {
            case EUR_CODE:
            case RUB_CODE:
            case USD_CODE:
                return contextView.getContext().getDrawable(R.drawable.shadow);
            default:
                return contextView.getContext().getDrawable(R.drawable.shadow);
        }
    }

}
