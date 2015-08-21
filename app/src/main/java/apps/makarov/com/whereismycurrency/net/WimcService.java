package apps.makarov.com.whereismycurrency.net;

import java.util.Date;
import java.util.List;

import apps.makarov.com.whereismycurrency.models.CurrencyPairData;
import apps.makarov.com.whereismycurrency.models.RateData;
import apps.makarov.com.whereismycurrency.models.ResultOperationData;
import apps.makarov.com.whereismycurrency.models.UserHistoryData;
import rx.Observable;

public interface WimcService {

    Observable<List<RateData>> getRatesAllBank(CurrencyPairData currencyPair);

    Observable<List<RateData>> getRates(CurrencyPairData currencyPair, Date date);

    Observable<List<UserHistoryData>> getHistory();

    Observable<List<ResultOperationData>> getResultOperations();

    Observable<List<ResultOperationData>> getUpdateResultOperations();

    ResultOperationData getResultOperation(String key);

    UserHistoryData addHistoryItem(CurrencyPairData currencyPair, final Date date, double summa, double rateValue);

    ResultOperationData addResult(RateData rate, UserHistoryData userHistory);

    ResultOperationData addResultInHistory(ResultOperationData resultOperation);

    void removeResult(ResultOperationData resultOperation);

}
