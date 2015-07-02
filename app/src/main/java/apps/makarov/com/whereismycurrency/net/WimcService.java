package apps.makarov.com.whereismycurrency.net;

import java.util.Date;
import java.util.List;

import apps.makarov.com.whereismycurrency.models.CurrencyPair;
import apps.makarov.com.whereismycurrency.models.Rate;
import apps.makarov.com.whereismycurrency.models.ResultOperation;
import apps.makarov.com.whereismycurrency.models.UserHistory;
import rx.Observable;

public interface WimcService {

    Observable<List<Rate>> getRatesAllBank(CurrencyPair currencyPair);

    Observable<List<Rate>> getHistoryRates(CurrencyPair currencyPair, Date date);

    Observable<List<UserHistory>> getHistory();

    ResultOperation getResultOperation(String key);

    UserHistory addHistoryItem(CurrencyPair currencyPair, final Date date, double summa, double rateValue);

    ResultOperation addResult(Rate rate, UserHistory userHistory);

}
