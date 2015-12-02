package apps.makarov.com.whereismycurrency;

import android.content.Context;

import java.math.BigDecimal;

import apps.makarov.com.whereismycurrency.models.Rate;
import apps.makarov.com.whereismycurrency.models.ResultOperation;

/**
 * Created by makarov on 28/07/15.
 */
public class ResultUtils {

    public static String getBaseValueString(Context context, ResultOperation operation){
        return context.getString(R.string.history_item_subtitle,
                operation.getUserHistory().getValue(),
                operation.getUserHistory().getRate().getCurrencyPair().getBaseCurrency());
    }

    public static double getDiff(ResultOperation operation){

        double startValue = operation.getUserHistory().getValue();
        double finishValue = getFinishValue(operation);

        return BigDecimal.valueOf(finishValue - startValue).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    public static double getDiff(ResultOperation operation, Rate rate){

        double startValue = operation.getUserHistory().getValue();
        double finishValue = getFinishValue(operation, rate);

        return BigDecimal.valueOf(finishValue - startValue).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    public static String getDiffStr(ResultOperation operation){

        double diff = getDiff(operation);

        return diff > 0 ? "+" + diff : diff + "";
    }

    public static String getDiffStr(ResultOperation operation, Rate rate){

        double diff = getDiff(operation, rate);

        return diff > 0 ? "+" + diff : diff + "";
    }

    public static String getFinishValueStr(Context context, ResultOperation operation){
        String str = BigDecimal.valueOf(getFinishValue(operation)).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue() + "";
        return context.getString(R.string.history_balance, str);
    }

    public static String getFinishValueStr(ResultOperation operation, Rate rate){
        String str = BigDecimal.valueOf(getFinishValue(operation, rate)).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue() + "";
        return  str;
    }

    public static double getOpenOperaionBaseValue(ResultOperation operation){
        double value = operation.getUserHistory().getValue();
        return BigDecimal.valueOf(value).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    public static double getFinishFirstOperationValue(ResultOperation operation){
        double value = operation.getUserHistory().getValue() * operation.getUserHistory().getRate().getValue();
        return BigDecimal.valueOf(value).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    public static double getFinishValue(ResultOperation operation) {
        double value = operation.getUserHistory().getValue() * operation.getUserHistory().getRate().getValue() * operation.getExitRate().getValue();
        return BigDecimal.valueOf(value).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    public static double getFinishValue(ResultOperation operation, Rate rate) {
        double value = operation.getUserHistory().getValue() * rate.getValue() * operation.getExitRate().getValue();
        return BigDecimal.valueOf(value).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    public static double getStartOperationValue(double value){
        return BigDecimal.valueOf(value).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    public static String getRoundRateValue(double value){
        String str = BigDecimal.valueOf(value).setScale(5, BigDecimal.ROUND_HALF_DOWN).doubleValue()+ "";
        return str;
    }

    public static double getFinishOperationValue(Rate rate, double value){
        double result = value * rate.getValue();
        return BigDecimal.valueOf(result).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    public static String getDiffStrRateValue(ResultOperation operation, Rate rate){

        double diff = getDiffRateValue(operation, rate);

        return diff > 0 ? "+" + diff : diff + "";
    }

    public static double getDiffRateValue(ResultOperation operation, Rate rate){
        double startValue = operation.getUserHistory().getRate().getValue();
        double finishValue = rate.getValue();

        return BigDecimal.valueOf(finishValue - startValue).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }


}
