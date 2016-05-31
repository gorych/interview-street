package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.GenericDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Generic DAO
 * @param <E> - сущность
 * @param <K> - ключ(тип идентификатора)
 */
@SuppressWarnings("unchecked")
@Repository
public abstract class GenericDAOImpl<E, K extends Serializable> implements GenericDAO<E, K> {

    protected Class<? extends E> daoType;

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * By defining this class as abstract, we prevent Spring from creating
     * instance of this class If not defined as abstract,
     * getClass().getGenericSuperClass() would return Object. There would be
     * exception because Object class does not have a constructor with parameters.
     */
    public GenericDAOImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        daoType = (Class) pt.getActualTypeArguments()[0];
    }

    protected Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Доабавляет сузность в БД
     * @param entity добавляемая сушность
     */
    @Override
    public void add(E entity) {
        currentSession().save(entity);
    }

    /**
     * Сохраняет или обновляет существующую сущность
     * @param entity обновляемая сущность
     */
    @Override
    public void saveOrUpdate(E entity) {
        currentSession().saveOrUpdate(entity);
    }

    /**
     * Удаляет сущность из БД
     * @param entity удаляемая сущность
     */
    @Override
    public void remove(E entity) {
        currentSession().delete(entity);
    }

    /**
     * Получает сущность из БД по ключу
     * @param key ключ
     * @return полученная сущность
     */
    @Override
    public E find(K key) {
        return (E) currentSession().get(daoType, key);
    }

    /**
     * Получает все сущности из БД
     * @return список сущностей
     */
    @Override
    public List<E> getAll() {
        return currentSession().createCriteria(daoType).list();
    }
}
