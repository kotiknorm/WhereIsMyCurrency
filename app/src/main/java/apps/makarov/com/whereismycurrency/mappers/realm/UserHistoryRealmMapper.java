package apps.makarov.com.whereismycurrency.mappers.realm;

import apps.makarov.com.whereismycurrency.mappers.Mapper;
import apps.makarov.com.whereismycurrency.models.UserHistory;
import apps.makarov.com.whereismycurrency.repository.realm.models.UserHistoryRealm;
import apps.makarov.com.whereismycurrency.repository.protocols.UserHistoryProtocol;

/**
 * Created by makarov on 11/08/15.
 */
public class UserHistoryRealmMapper implements Mapper<UserHistory, UserHistoryProtocol> {

    RateRealmMapper rateRealmMapper = new RateRealmMapper();

    @Override
    public UserHistory modelToData(UserHistoryProtocol obj) {
        return null;
    }

    @Override
    public UserHistoryRealm dataToModel(UserHistory userHistoryData) {
        UserHistoryRealm userHistory = new UserHistoryRealm();
        userHistory.setValue(userHistoryData.getValue());
        userHistory.setRate(rateRealmMapper.dataToModel(userHistoryData.getRate()));
        userHistory.setDate(userHistoryData.getDate());
        userHistory.setKey(generateKey(userHistoryData));
        return userHistory;
    }


    public static String generateKey(UserHistory userHistory) {
        return userHistory.getDate().toString();
    }

}
