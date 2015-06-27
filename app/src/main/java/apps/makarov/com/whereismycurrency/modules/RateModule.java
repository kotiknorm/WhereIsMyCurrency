package apps.makarov.com.whereismycurrency.modules;

import apps.makarov.com.whereismycurrency.net.GMHService;
import apps.makarov.com.whereismycurrency.presenters.RatePresenter;
import apps.makarov.com.whereismycurrency.presenters.RatePresenterIml;
import apps.makarov.com.whereismycurrency.view.fragments.RateFragment;
import apps.makarov.com.whereismycurrency.view.iviews.RateView;
import dagger.Module;
import dagger.Provides;

/**
 * Created by makarov on 26/06/15.
 */

@Module(
        injects = RateFragment.class,
        addsTo = GMHModule.class,
        complete = false
)
public final class RateModule {
    public static final String TAG = RateModule.class.getSimpleName();

    private RateView mHotView;

    public RateModule(RateView hotView) {
        this.mHotView = hotView;
    }

    @Provides
    public RateView provideView() {
        return mHotView;
    }

    @Provides
    public RatePresenter providePresenter(RateView rateView, GMHService gmhService) {
        return new RatePresenterIml(rateView, gmhService);
    }
}