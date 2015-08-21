package apps.makarov.com.whereismycurrency.mappers;

import apps.makarov.com.whereismycurrency.models.ResultOperationData;
import apps.makarov.com.whereismycurrency.repository.models.ResultOperation;

/**
 * Created by makarov on 11/08/15.
 */
public class ResultOperationMapper implements Mapper<ResultOperationData, ResultOperation> {

    private UserHistoryMapper userHistoryMapper = new UserHistoryMapper();
    private RateMapper rateMapper = new RateMapper();

    @Override
    public ResultOperationData modelToData(ResultOperation obj) {
        return null;
    }

    @Override
    public ResultOperation dataToModel(ResultOperationData resultOperationData) {
        ResultOperation pair = new ResultOperation();
        pair.setExitRate(rateMapper.dataToModel(resultOperationData.getExitRate()));
        pair.setUserHistory(userHistoryMapper.dataToModel(resultOperationData.getUserHistory()));
        pair.setIsHistory(resultOperationData.isHistory());
        pair.setDate(resultOperationData.getDate());
        pair.setKey(generateKey(resultOperationData));
        return pair;
    }

    public static String generateKey(ResultOperationData resultOperation) {
        return resultOperation.getDate().toString();
    }
}
