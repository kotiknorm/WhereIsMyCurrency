package apps.makarov.com.whereismycurrency;

import android.content.Context;

import java.math.BigDecimal;

import apps.makarov.com.whereismycurrency.models.Rate;

/**
 * Created by makarov on 28/07/15.
 */
public class ResultUtils {

    public static final int SIGNIFICANT_ROUND_SCALE_VALUE = 2;
    public static final int NOT_SIGNIFICANT_ROUND_SCALE_VALUE = 5;


    //
    public static String getStrFinishBalance(Context context, double value, Rate historyRate, Rate rate){
        String str = getStrRoundSignificant(getFinishClosingOperation(value, historyRate, rate));
        return context.getString(R.string.history_balance, str);
    }

    //
    public static String getStrBaseValue(Context context, double value, Rate historyRate){
        return context.getString(R.string.history_item_subtitle,
                value, historyRate.getCurrencyPair().getBaseCurrency());
    }

    //*  (getDiffStr)
    public static String getStrProfitClosingOperation(double value, Rate historyRate, Rate rate){

        double diff = getProfitClosingOperation(value, historyRate, rate);
        String str = getStrRoundSignificant(diff);

        return diff > 0 ? "+" + str : str ;
    }

    //*прибыль при закрытии операции  (getDiff)
    public static double getProfitClosingOperation(double value, Rate historyRate, Rate rate){

        double startValue = value;
        double finishValue = getFinishClosingOperation(value, historyRate, rate);

        return getDiff(finishValue, startValue);
    }

    public static String getStrDiffRateValue(Rate baseRate, Rate rate){

        double diff = getDiff(rate.getValue(), baseRate.getValue());
        String str = getStrRoundNotSignificant(diff);

        return diff > 0 ? "+" + str : str ;
    }

    //*конечное значение при закрытии операции   (getFinishValue)
    public static double getFinishClosingOperation(double value, Rate historyRate, Rate rate) {
        double result = value * historyRate.getValue() * rate.getValue();
        return result;
    }

    public static double getFinishResultOperation(double value, Rate rate){
        double result = value * rate.getValue();
        return result;
    }

    //*разница между двумя значениями
    public static double getDiff(double startValue, double finishValue){
        return startValue - finishValue;
    }

    //*
    public static String getStrRoundNotSignificant(double value){
        String str = getRoundNotSignificant(value) + "";
        return str;
    }

    //*
    public static String getStrRoundSignificant(double value){
        String str = getRoundSignificant(value) + "";
        return str;
    }

    //*не существенное округлени(до 5 после запятой)
    private static BigDecimal getRoundNotSignificant(double value){
        return new BigDecimal("" + value).setScale(NOT_SIGNIFICANT_ROUND_SCALE_VALUE, BigDecimal.ROUND_HALF_DOWN);
    }

    //*существенное округление(до 2 после запятой)
    private static BigDecimal getRoundSignificant(double value){
        return new BigDecimal("" + value).setScale(SIGNIFICANT_ROUND_SCALE_VALUE, BigDecimal.ROUND_HALF_DOWN);
    }

}
