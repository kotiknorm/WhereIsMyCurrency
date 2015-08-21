package apps.makarov.com.whereismycurrency.mappers;

import apps.makarov.com.whereismycurrency.models.UserHistoryData;
import apps.makarov.com.whereismycurrency.repository.models.UserHistory;

/**
 * Created by makarov on 11/08/15.
 */
public class UserHistoryMapper implements Mapper<UserHistoryData, UserHistory> {

    RateMapper rateMapper = new RateMapper();

    @Override
    public UserHistoryData modelToData(UserHistory obj) {
        return null;
    }

    @Override
    public UserHistory dataToModel(UserHistoryData userHistoryData) {
        UserHistory userHistory = new UserHistory();
        userHistory.setValue(userHistoryData.getValue());
        userHistory.setRate(rateMapper.dataToModel(userHistoryData.getRate()));
        userHistory.setDate(userHistoryData.getDate());
        userHistory.setKey(generateKey(userHistoryData));
        return userHistory;
    }


    public static String generateKey(UserHistoryData userHistory) {
        return userHistory.getDate().toString();
    }

}
