package apps.makarov.com.whereismycurrency.modules;

import apps.makarov.com.whereismycurrency.net.WimcService;
import apps.makarov.com.whereismycurrency.presenters.BanksListPresenter;
import apps.makarov.com.whereismycurrency.presenters.BanksListPresenterImpl;
import apps.makarov.com.whereismycurrency.view.fragments.BanksListFragment;
import apps.makarov.com.whereismycurrency.view.iviews.BanksView;
import dagger.Module;
import dagger.Provides;

/**
 * Created by makarov on 04/08/15.
 */

@Module(
        injects = BanksListFragment.class,
        addsTo = WimcModule.class,
        complete = false
)
public final class BanksListModule {
    public static final String TAG = ListOperationModule.class.getSimpleName();

    private BanksView mHotView;

    public BanksListModule(BanksView hotView) {
        this.mHotView = hotView;
    }

    @Provides
    public BanksView provideView() {
        return mHotView;
    }

    @Provides
    public BanksListPresenter providePresenter(BanksView banksView, WimcService wimcService) {
        return new BanksListPresenterImpl(banksView, wimcService);
    }
}

