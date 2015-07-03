package apps.makarov.com.whereismycurrency.modules;

import apps.makarov.com.whereismycurrency.net.WimcService;
import apps.makarov.com.whereismycurrency.presenters.ListOperationPresenter;
import apps.makarov.com.whereismycurrency.presenters.ListOperationPresenterImpl;
import apps.makarov.com.whereismycurrency.view.fragments.ListOperationFragment;
import apps.makarov.com.whereismycurrency.view.iviews.ListOperationView;
import dagger.Module;
import dagger.Provides;

/**
 * Created by makarov on 03/07/15.
 */

@Module(
        injects = ListOperationFragment.class,
        addsTo = WimcModule.class,
        complete = false
)
public final class ListOperationModule {
    public static final String TAG = ListOperationModule.class.getSimpleName();

    private ListOperationView mHotView;

    public ListOperationModule(ListOperationView hotView) {
        this.mHotView = hotView;
    }

    @Provides
    public ListOperationView provideView() {
        return mHotView;
    }

    @Provides
    public ListOperationPresenter providePresenter(ListOperationView listOperationView, WimcService wimcService) {
        return new ListOperationPresenterImpl(listOperationView, wimcService);
    }
}
