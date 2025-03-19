package Interfaces;

import java.util.List;

public interface GenericDAO<T> {
    void save(T object);
    void delete(T object);
    void update(T object);
    List<T> list();
    boolean isFieldExists(String fieldName, String currentValue);
}
