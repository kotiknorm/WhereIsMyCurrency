package apps.makarov.com.whereismycurrency;

import java.util.Arrays;
import java.util.List;

import apps.makarov.com.whereismycurrency.modules.WimcModule;

/**
 * Created by makarov on 26/06/15.
 */
public final class Modules {
    public static final String TAG = Modules.class.getSimpleName();

    private Modules() {
        throw new AssertionError(TAG + ": Cannot be initialized.");
    }

    public static List<Object> getModules(WimcApplication application) {
        return Arrays.<Object>asList(
                new WimcModule(application)
        );
    }
}
