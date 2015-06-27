package apps.makarov.com.whereismycurrency.modules;

import android.app.Application;

import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import apps.makarov.com.whereismycurrency.database.IStore;
import apps.makarov.com.whereismycurrency.database.RealmStore;
import apps.makarov.com.whereismycurrency.net.GMHService;
import apps.makarov.com.whereismycurrency.net.GMHServiceImpl;
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

    public static final String API_URL = "http://mobile.givesmehope.com";
    public static final String INITIAL_TRENDING_PAGE_URL = "http://mobile.givesmehope.com/page/1/";
    public static final String INITIAL_HOT_PAGE_URL = "http://mobile.givesmehope.com/bestof/month/";
    public static final String SUBMIT_URL = "http://mobile.givesmehope.com/submit_new.php";
    public static final String VOTE_URL = "http://mobile.givesmehope.com/moderate";
    public static final String VOTE_ACTION_URL = "http://mobile.givesmehope.com/script/post_action.php";

    public static final int DISK_CACHE_SIZE = 50 * 1024 * 1024;
    public static final int PULL_TOLERANCE = 10;

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(Application application) {
        return createOkHttpClient(application);
    }

    @Provides
    @Singleton
    public IStore provideIStore(Application application) {
        return new RealmStore(application);
    }

    @Provides
    @Singleton
    public GMHService provideGMHService(OkHttpClient client, IStore store) {
        return new GMHServiceImpl(client, store);
    }

    private static OkHttpClient createOkHttpClient(Application application) {
        final OkHttpClient temporaryClient = new OkHttpClient();

//        try {
//            File cacheDirectory = application.getCacheDir();
//            Cache cache = new Cache(cacheDirectory, DISK_CACHE_SIZE);
//            temporaryClient.setCache(cache);
//        } catch (Exception e) {
//            if (BuildConfig.DEBUG) {
//                Log.e(TAG, "Unable to initialize OkHttpClient with disk cache.");
//            }
//        }


        return temporaryClient;
    }
}
