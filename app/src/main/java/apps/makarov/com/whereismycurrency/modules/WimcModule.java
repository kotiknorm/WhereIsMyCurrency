package apps.makarov.com.whereismycurrency.modules;

import android.app.Application;

import javax.inject.Singleton;

import apps.makarov.com.whereismycurrency.WimcApplication;
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
                WimcApplication.class
        }
)
public final class WimcModule {
    public static final String TAG = WimcModule.class.getSimpleName();

    private final WimcApplication mApplication;

    public WimcModule(WimcApplication application) {
        this.mApplication = application;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return mApplication;
    }
}
