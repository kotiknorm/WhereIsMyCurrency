package apps.makarov.com.whereismycurrency.net;

import java.util.Date;
import java.util.List;

import apps.makarov.com.whereismycurrency.models.Bank;
import apps.makarov.com.whereismycurrency.models.Rate;
import apps.makarov.com.whereismycurrency.models.UserHistory;
import rx.Observable;

public interface WimcService {

    //Network data
    Observable<List<Bank>> getAllBank();

    Observable<List<Rate>> getRatesFromBank(String bankName);

    Observable<List<Rate>> getAllRates();

    Observable<Rate> getRate(String baseCurrency, String compareCurrency, Date date);

    Observable<List<UserHistory>> getHistory();

    void addHistoryItem(UserHistory historyItem);

}
