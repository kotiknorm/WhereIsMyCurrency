package apps.makarov.com.whereismycurrency.net;

import java.util.Date;
import java.util.List;

import apps.makarov.com.whereismycurrency.models.Rate;
import apps.makarov.com.whereismycurrency.models.ResultOperation;
import apps.makarov.com.whereismycurrency.models.UserHistory;
import rx.Observable;

public interface WimcService {

    Observable<List<Rate>> getRatesAllBank(String baseCurrency, String compareCurrency);

    Observable<List<Rate>> getHistoryRates(String baseCurrency, String compareCurrency, Date date);

    Observable<List<UserHistory>> getHistory();

    UserHistory addHistoryItem(final String baseCurrency, final String compareCurrency, final Date date, double rateValue, double value);

    ResultOperation addResult(Rate rate, UserHistory userHistory);

    ResultOperation getResultOperation(String key);

}
