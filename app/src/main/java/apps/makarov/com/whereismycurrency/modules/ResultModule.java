package apps.makarov.com.whereismycurrency.modules;

import apps.makarov.com.whereismycurrency.net.WimcService;
import apps.makarov.com.whereismycurrency.presenters.ResultPresenter;
import apps.makarov.com.whereismycurrency.presenters.ResultPresenterImpl;
import apps.makarov.com.whereismycurrency.view.fragments.ResultFragment;
import apps.makarov.com.whereismycurrency.view.iviews.ResultView;
import dagger.Module;
import dagger.Provides;

/**
 * Created by makarov on 01/07/15.
 */

@Module(
        injects = ResultFragment.class,
        addsTo = WimcModule.class,
        complete = false
)
public final class ResultModule {
    public static final String TAG = ResultModule.class.getSimpleName();

    private ResultView mResultView;

    public ResultModule(ResultView resultView) {
        this.mResultView = resultView;
    }

    @Provides
    public ResultView provideView() {
        return mResultView;
    }

    @Provides
    public ResultPresenter providePresenter(ResultView resultView, WimcService wimcService) {
        return new ResultPresenterImpl(resultView, wimcService);
    }
}