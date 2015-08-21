package apps.makarov.com.whereismycurrency.modules;

import apps.makarov.com.whereismycurrency.mappers.BankMapper;
import apps.makarov.com.whereismycurrency.mappers.CurrencyPairMapper;
import apps.makarov.com.whereismycurrency.mappers.RateMapper;
import apps.makarov.com.whereismycurrency.mappers.ResultOperationMapper;
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
    public BankMapper provideBankMapper() {
        return new BankMapper();
    }

    @Provides
    public RateMapper provideRateMapper() {
        return new RateMapper();
    }

    @Provides
    public CurrencyPairMapper provideCurrencyPairMapper() {
        return new CurrencyPairMapper();
    }

    @Provides
    public ResultOperationMapper provideResultOperationMapper() {
        return new ResultOperationMapper();
    }

}