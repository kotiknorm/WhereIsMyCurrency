package apps.makarov.com.whereismycurrency.mappers;

/**
 * Created by makarov on 11/08/15.
 */
public interface Mapper<C, T> {

    C modelToData(T obj);

    T dataToModel(C obj);
}
