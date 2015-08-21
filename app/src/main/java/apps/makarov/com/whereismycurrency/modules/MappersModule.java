package apps.makarov.com.whereismycurrency.modules;

import apps.makarov.com.whereismycurrency.mappers.realm.BankRealmMapper;
import apps.makarov.com.whereismycurrency.mappers.realm.CurrencyPairRealmMapper;
import apps.makarov.com.whereismycurrency.mappers.realm.RateRealmMapper;
import apps.makarov.com.whereismycurrency.mappers.realm.ResultOperationRealmMapper;
import dagger.Module;
import dagger.Provides;

/**
 * Created by makarov on 12/08/15.
 */

@Module(
        complete = false,
        library = true
)
public class MappersModule {

    public static final String TAG = MappersModule.class.getSimpleName();

    public MappersModule(){
    }

    @Provides
    public BankRealmMapper provideBankMapper() {
        return new BankRealmMapper();
    }

    @Provides
    public RateRealmMapper provideRateMapper() {
        return new RateRealmMapper();
    }

    @Provides
    public CurrencyPairRealmMapper provideCurrencyPairMapper() {
        return new CurrencyPairRealmMapper();
    }

    @Provides
    public ResultOperationRealmMapper provideResultOperationMapper() {
        return new ResultOperationRealmMapper();
    }

}