package apps.makarov.com.whereismycurrency.mappers;

/**
 * Created by makarov on 11/08/15.
 */
public interface Mapper<C, T> {

    C dataToModel(T objData);

    T modelToData(C objModel);
}
