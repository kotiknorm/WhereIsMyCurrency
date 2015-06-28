package apps.makarov.com.whereismycurrency.net;

import java.util.List;

import apps.makarov.com.whereismycurrency.models.Bank;
import apps.makarov.com.whereismycurrency.models.Rate;
import apps.makarov.com.whereismycurrency.models.UserHistory;
import rx.Observable;

public interface WimcService {

    //Network data
    Observable<List<Bank>> getAllBank();

    Observable<List<Rate>> getRateFromBank(String bankName);

    Observable<List<UserHistory>> getHistory();

    void addHistoryItem(UserHistory historyItem);

}
