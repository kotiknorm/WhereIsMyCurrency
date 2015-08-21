package apps.makarov.com.whereismycurrency;

import android.content.Context;

import java.math.BigDecimal;

import apps.makarov.com.whereismycurrency.models.RateData;
import apps.makarov.com.whereismycurrency.models.ResultOperationData;

/**
 * Created by makarov on 28/07/15.
 */
public class ResultUtils {

    public static String getBaseValueString(Context context, ResultOperationData operation){
        return context.getString(R.string.history_item_subtitle,
                operation.getUserHistory().getValue(),
                operation.getUserHistory().getRate().getCurrencyPair().getBaseCurrency());
    }

    public static double getDiff(ResultOperationData operation){

        double startValue = operation.getUserHistory().getValue();
        double finishValue = getFinishValue(operation);

        return BigDecimal.valueOf(finishValue - startValue).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    public static String getDiffStr(ResultOperationData operation){

        double diff = getDiff(operation);

        return diff > 0 ? "+" + diff : diff + "";
    }

    public static String getFinishValueStr(Context context, ResultOperationData operation){
        String str = BigDecimal.valueOf(getFinishValue(operation)).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue() + "";
        return context.getString(R.string.history_balance, str);
    }

    public static double getOpenOperaionBaseValue(ResultOperationData operation){
        double value = operation.getUserHistory().getValue();
        return BigDecimal.valueOf(value).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    public static double getFinishFirstOperationValue(ResultOperationData operation){
        double value = operation.getUserHistory().getValue() * operation.getUserHistory().getRate().getValue();
        return BigDecimal.valueOf(value).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    public static double getFinishValue(ResultOperationData operation) {
        double value = operation.getUserHistory().getValue() * operation.getUserHistory().getRate().getValue() * operation.getExitRate().getValue();
        return BigDecimal.valueOf(value).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    public static double getStartOperationValue(double value){
        return BigDecimal.valueOf(value).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    public static double getFinishOperationValue(RateData rate, double value){
        double result = value * rate.getValue();
        return BigDecimal.valueOf(result).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }




}
