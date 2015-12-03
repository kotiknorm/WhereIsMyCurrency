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

    Observable<List<Rate>> getRates(CurrencyPair currencyPair, Date date);

    Observable<List<Rate>> getAllRatesByCurrencyPair(CurrencyPair currencyPair);

    Observable<List<UserHistory>> getHistory();

    Observable<List<ResultOperation>> getResultOperations();

    Observable<List<ResultOperation>> getUpdateResultOperations();

    ResultOperation getResultOperation(String key);

    UserHistory addHistoryItem(CurrencyPair currencyPair, final Date date, double summa, double rateValue);

    ResultOperation addResult(Rate rate, UserHistory userHistory);

    ResultOperation addResultInHistory(ResultOperation resultOperation);

    void removeResult(ResultOperation resultOperation);

    int getSizeAllRatesByCurrencyPair(final CurrencyPair currencyPair);
}
