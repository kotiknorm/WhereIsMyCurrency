package apps.makarov.com.whereismycurrency.view.iviews;

import android.graphics.drawable.Drawable;

import apps.makarov.com.whereismycurrency.view.base.BaseContextView;

/**
 * Created by makarov on 01/07/15.
 */
public interface ResultView extends BaseContextView {

    void setDiffValue(double diff);

    void setColorForResult(int colorRes);

    void setExitCompareIcon(Drawable drawableRes);

    void setExitBaseIcon(Drawable drawableRes);

    void setOpenCompareIcon(Drawable drawableRes);

    void setOpenBaseIcon(Drawable drawableRes);

    void setExitOperaionCompareValue(double exitOperaionCompareValue);

    void setExitOperaionBaseValue(double exitOperaionBaseValue);

    void setOpenOperaionCompareValue(double openOperaionCompareValue);

    void setOpenOperaionBaseValue(double openOperaionBaseValue);

    void setOpenOperationBaseCurrencyName(String value);

    void setOpenOperationCompareCurrencyName(String value);

    void setExitOperationBaseCurrencyName(String value);

    void setExitOperationCompareCurrencyName(String value);

}
