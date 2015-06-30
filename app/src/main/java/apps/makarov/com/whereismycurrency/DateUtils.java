package apps.makarov.com.whereismycurrency;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by makarov on 30/06/15.
 */
public class DateUtils {

    public static Date getTodayDate(){
        Calendar today = Calendar.getInstance();
        today.clear(Calendar.HOUR);
        today.clear(Calendar.MINUTE);
        today.clear(Calendar.SECOND);
        today.clear(Calendar.MILLISECOND);
        return today.getTime();
    }

    public static boolean isToday(Date date){
        return getTodayDate().equals(date);
    }

    public static SimpleDateFormat getFixerIoDareFormat(){
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    public static SimpleDateFormat getWimcFormat(){
        return new SimpleDateFormat("yyyy-MM-dd");
    }

}
