package apps.makarov.com.whereismycurrency.presenters;

import apps.makarov.com.whereismycurrency.models.RateData;
import apps.makarov.com.whereismycurrency.net.WimcService;
import apps.makarov.com.whereismycurrency.view.iviews.BanksView;

/**
 * Created by makarov on 04/08/15.
 */
public class BanksListPresenterImpl  implements BanksListPresenter {

    private static final String TAG = ListOperationPresenterImpl.class.getSimpleName();

    private BanksView mBanksView;
    private WimcService mWimcService;

    private RateData mRate;

    public BanksListPresenterImpl(BanksView banksView, WimcService wimcService) {
        this.mBanksView = banksView;
        this.mWimcService = wimcService;
    }

    @Override
    public void onResume() {
        onRefresh();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void setRate(String resultKey) {
        // здесь вся логика заполнения списка  полей вью элемента
    }

    private void onRefresh() {

    }


}
