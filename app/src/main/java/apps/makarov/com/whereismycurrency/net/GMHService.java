package apps.makarov.com.whereismycurrency.net;

import java.util.List;

import apps.makarov.com.whereismycurrency.models.Bank;
import apps.makarov.com.whereismycurrency.models.Rate;
import rx.Observable;

public interface GMHService {

    public Observable<List<Bank>> getAllBank(String requestUrl);

    public Observable<List<Rate>> getRateFromBank(String bankName);

}
