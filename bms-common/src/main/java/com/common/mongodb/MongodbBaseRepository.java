package com.common.mongodb;

import com.mongodb.WriteResult;

import java.util.List;
import java.util.Map;

public interface MongodbBaseRepository<T> {
    List<T> findAll(final Map<String, Object> p0, final Class<T> p1);

    List<T> findAll(final Class<T> p0);

    T findOne(final Map<String, Object> p0, final Class<T> p1);

    void save(final T p0);

    WriteResult remove(final Map<String, Object> p0, final Class<T> p1);

    T findAndModify(final Map<String, Object> p0, final Map<String, Object> p1, final Class<T> p2);

    WriteResult findAndModifyAll(final Map<String, Object> p0, final Map<String, Object> p1, final Class<T> p2);

    T findOneSortAsc(Map<String, Object> params, Class<T> entityClass);

    T findOneSortDesc(Map<String, Object> params, Class<T> entityClass);
}