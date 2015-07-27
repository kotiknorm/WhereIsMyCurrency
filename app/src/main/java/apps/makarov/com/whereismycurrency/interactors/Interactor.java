package apps.makarov.com.whereismycurrency.interactors;

import java.util.concurrent.Callable;

/**
 * Created by makarov on 12/07/15.
 */

public interface Interactor<T, E extends Exception> extends Callable<T> {
    @Override T call() throws E;
}
