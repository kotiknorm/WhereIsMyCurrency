package apps.makarov.com.whereismycurrency.modules;

import apps.makarov.com.whereismycurrency.net.WimcService;
import apps.makarov.com.whereismycurrency.presenters.EnterOperationPresenter;
import apps.makarov.com.whereismycurrency.presenters.EnterOperationPresenterImpl;
import apps.makarov.com.whereismycurrency.view.fragments.EnterOperationFragment;
import apps.makarov.com.whereismycurrency.view.iviews.EnterOperationView;
import dagger.Module;
import dagger.Provides;

/**
 * Created by makarov on 26/06/15.
 */

@Module(
        injects = EnterOperationFragment.class,
        addsTo = WimcModule.class,
        complete = false
)
public final class EnterOperationModule {
    public static final String TAG = EnterOperationModule.class.getSimpleName();

    private EnterOperationView mHotView;

    public EnterOperationModule(EnterOperationView hotView) {
        this.mHotView = hotView;
    }

    @Provides
    public EnterOperationView provideView() {
        return mHotView;
    }

    @Provides
    public EnterOperationPresenter providePresenter(EnterOperationView enterOperationView, WimcService wimcService) {
        return new EnterOperationPresenterImpl(enterOperationView, wimcService);
    }
}