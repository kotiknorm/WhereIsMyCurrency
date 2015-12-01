package apps.makarov.com.whereismycurrency.view.iviews;

import java.util.List;
import apps.makarov.com.whereismycurrency.models.Rate;
import apps.makarov.com.whereismycurrency.models.ResultOperation;

/**
 * Created by makarov on 04/08/15.
 */
public interface BanksView {

    void setAdapterForRecyclerView(List<Rate> banks);

    void bindModelToView(ResultOperation history);

    void setVisibleSplash(boolean isShown);

}
