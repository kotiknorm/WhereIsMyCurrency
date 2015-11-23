package apps.makarov.com.whereismycurrency.repository.protocols;

import java.util.Date;

import apps.makarov.com.whereismycurrency.models.UniqueModel;

/**
 * Created by makarov on 22/08/15.
 */
public interface ResultOperationProtocol
        <T extends UserHistoryProtocol, E extends RateProtocol> extends UniqueModel {

    T getUserHistory();

    void setUserHistory(T userHistory);

    void setKey(String key);

    void setExitRate(E exitRate);

    Date getDate();

    void setDate(Date date);

    boolean isHistory();

    void setIsHistory(boolean isHistory);

    E getExitRate();
}
