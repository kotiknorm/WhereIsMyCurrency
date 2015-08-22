package apps.makarov.com.whereismycurrency.modules;

import android.app.Application;
import android.util.Log;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;

import javax.inject.Singleton;

import apps.makarov.com.whereismycurrency.BuildConfig;
import apps.makarov.com.whereismycurrency.repository.IRepository;
import apps.makarov.com.whereismycurrency.repository.realm.RealmRepository;
import apps.makarov.com.whereismycurrency.net.WimcService;
import apps.makarov.com.whereismycurrency.net.WimcServiceImpl;
import dagger.Module;
import dagger.Provides;

/**
 * Created by makarov on 26/06/15.
 */

@Module(
        complete = false,
        library = true
)
public final class ApiModule {
    public static final String TAG = ApiModule.class.getSimpleName();

    public static final int DISK_CACHE_SIZE = 50 * 1024 * 1024;

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(Application application) {
        return createOkHttpClient(application);
    }

    @Provides
    @Singleton
    public IRepository provideIStore(Application application) {
        return new RealmRepository(application);
    }

    @Provides
    @Singleton
    public WimcService provideGMHService(OkHttpClient client, IRepository store) {
        return new WimcServiceImpl(client, store);
    }

    private static OkHttpClient createOkHttpClient(Application application) {
        final OkHttpClient temporaryClient = new OkHttpClient();
        temporaryClient.networkInterceptors().add(new StethoInterceptor());

        try {
            File cacheDirectory = application.getCacheDir();
            Cache cache = new Cache(cacheDirectory, DISK_CACHE_SIZE);
            temporaryClient.setCache(cache);
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, "Unable to initialize OkHttpClient with disk cache.");
            }
        }

        return temporaryClient;
    }
}
