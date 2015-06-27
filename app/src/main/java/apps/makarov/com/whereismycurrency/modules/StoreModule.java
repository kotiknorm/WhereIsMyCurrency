package apps.makarov.com.whereismycurrency.modules;

import android.app.Application;

import apps.makarov.com.whereismycurrency.database.IStore;
import apps.makarov.com.whereismycurrency.database.RealmStore;

/**
 * Created by makarov on 27/06/15.
 */

//@Module( injects = RateModule.class,
//        complete = false,
//        library = true
//
//)
public final class StoreModule {
    public static final String TAG = StoreModule.class.getSimpleName();

    private Application application;

    public StoreModule(Application application){
        this.application = application;
    }

//    @Provides
//    @Singleton
    public IStore provideIStore() {
        return new RealmStore(application);
    }
}

