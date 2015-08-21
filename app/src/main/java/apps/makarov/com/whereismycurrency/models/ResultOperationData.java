package apps.makarov.com.whereismycurrency.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by makarov on 01/07/15.
 */
public class ResultOperationData implements UniqueModel {

    private RateData exitRate;
    private UserHistoryData userHistory;
    private Date date;
    private boolean isHistory;

    public UserHistoryData getUserHistory() {
        return userHistory;
    }

    public void setUserHistory(UserHistoryData userHistory) {
        this.userHistory = userHistory;
    }

    public RateData getExitRate() {
        return exitRate;
    }

    public void setExitRate(RateData exitRate) {
        this.exitRate = exitRate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isHistory() {
        return isHistory;
    }

    public void setIsHistory(boolean isHistory) {
        this.isHistory = isHistory;
    }

    public static List<ResultOperationData> findActiveOperation(List<ResultOperationData> list) {
        List<ResultOperationData> resultList = new ArrayList<>();
        for(ResultOperationData operation : list){
            if(!operation.isHistory())
                resultList.add(operation);
        }
        return resultList;
    }

    public static List<ResultOperationData> findHistoryOperation(List<ResultOperationData> list) {
        List<ResultOperationData> resultList = new ArrayList<>();
        for(ResultOperationData operation : list){
            if(operation.isHistory())
                resultList.add(operation);
        }
        return resultList;
    }


    @Override
    public String getKey() {
        return null;
    }
}
