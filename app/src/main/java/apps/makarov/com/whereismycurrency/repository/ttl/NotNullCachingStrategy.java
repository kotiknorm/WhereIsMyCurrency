package apps.makarov.com.whereismycurrency.repository.ttl;

public class NotNullCachingStrategy<T> implements CachingStrategy<T> {
  @Override
  public boolean isValid(T data) {
    return data != null;
  }
}
