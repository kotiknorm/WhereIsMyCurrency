package apps.makarov.com.whereismycurrency.repository.ttl;

public interface CachingStrategy<T> {
  boolean isValid(T data);
}
