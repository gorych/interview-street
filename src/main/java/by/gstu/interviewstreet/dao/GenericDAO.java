package by.gstu.interviewstreet.dao;

import java.util.List;

public interface GenericDAO<E, K> {

    void add(E entity);

    void saveOrUpdate(E entity);

    void remove(E entity);

    E find(K key);

    List<E> getAll();

}
