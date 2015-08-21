package apps.makarov.com.whereismycurrency.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by makarov on 01/07/15.
 */
public class ResultOperation implements UniqueModel {

    private Rate exitRate;
    private UserHistory userHistory;
    private Date date;
    private boolean isHistory;

    public UserHistory getUserHistory() {
        return userHistory;
    }

    public void setUserHistory(UserHistory userHistory) {
        this.userHistory = userHistory;
    }

    public Rate getExitRate() {
        return exitRate;
    }

    public void setExitRate(Rate exitRate) {
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

    public static List<ResultOperation> findActiveOperation(List<ResultOperation> list) {
        List<ResultOperation> resultList = new ArrayList<>();
        for(ResultOperation operation : list){
            if(!operation.isHistory())
                resultList.add(operation);
        }
        return resultList;
    }

    public static List<ResultOperation> findHistoryOperation(List<ResultOperation> list) {
        List<ResultOperation> resultList = new ArrayList<>();
        for(ResultOperation operation : list){
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
