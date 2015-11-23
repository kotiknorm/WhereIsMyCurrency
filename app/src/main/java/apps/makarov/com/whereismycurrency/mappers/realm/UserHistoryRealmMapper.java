package apps.makarov.com.whereismycurrency.mappers.realm;

import apps.makarov.com.whereismycurrency.mappers.Mapper;
import apps.makarov.com.whereismycurrency.models.UserHistory;
import apps.makarov.com.whereismycurrency.repository.realm.models.UserHistoryRealm;
import apps.makarov.com.whereismycurrency.repository.protocols.UserHistoryProtocol;

/**
 * Created by makarov on 11/08/15.
 */
public class UserHistoryRealmMapper implements Mapper<UserHistory, UserHistoryProtocol> {

    private RateRealmMapper rateRealmMapper = new RateRealmMapper();

    @Override
    public UserHistory dataToModel(UserHistoryProtocol obj) {
        UserHistory userHistory = new UserHistory();
        userHistory.setValue(obj.getValue());
        userHistory.setRate(rateRealmMapper.dataToModel(obj.getRate()));
        userHistory.setDate(obj.getDate());
        //userHistory.setKey(generateKey(obj));
        return userHistory;
    }

    @Override
    public UserHistoryRealm modelToData(UserHistory userHistoryModel) {
        UserHistoryRealm userHistory = new UserHistoryRealm();
        userHistory.setValue(userHistoryModel.getValue());
        userHistory.setRate(rateRealmMapper.modelToData(userHistoryModel.getRate()));
        userHistory.setDate(userHistoryModel.getDate());
        userHistory.setKey(generateKey(userHistoryModel));
        return userHistory;
    }


    public static String generateKey(UserHistory userHistory) {
        return userHistory.getDate().toString();
    }

}
