package apps.makarov.com.whereismycurrency.net;

import java.util.Date;
import java.util.List;

import apps.makarov.com.whereismycurrency.models.Rate;
import apps.makarov.com.whereismycurrency.models.UserHistory;
import rx.Observable;

public interface WimcService {

    Observable<List<Rate>> getRatesAllBank(String baseCurrency, String compareCurrency);

    Observable<List<Rate>> getHistoryRates(String baseCurrency, String compareCurrency, Date date);

    Observable<List<UserHistory>> getHistory();

    void addHistoryItem(final String baseCurrency, final String compareCurrency, final Date date, double rateValue, double value);

}
