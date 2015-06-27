package apps.makarov.com.whereismycurrency.modules;

import android.app.Application;

import javax.inject.Singleton;

import apps.makarov.com.whereismycurrency.WmcApplication;
import dagger.Module;
import dagger.Provides;

/**
 * Created by makarov on 26/06/15.
 */

@Module(
        includes = {
                ApiModule.class
        },
        injects = {
                WmcApplication.class
        }
)
public final class WimcModule {
    public static final String TAG = WimcModule.class.getSimpleName();

    private final WmcApplication mApplication;

    public WimcModule(WmcApplication application) {
        this.mApplication = application;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return mApplication;
    }
}
