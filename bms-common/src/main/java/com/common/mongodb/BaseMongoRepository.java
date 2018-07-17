package com.common.mongodb;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * 共通MongoDB持久化操作类
 *
 * @author: XianjiCai
 * @date: 2018/02/01 13:40
 */
@Repository
public class BaseMongoRepository<T> extends AbstractMongodbBaseRepositoryImpl<T> {

    /**
     * 新增数据
     *
     * @param entity
     */
    @Override
    public void save(T entity) {
        long start = System.currentTimeMillis();
        boolean successFlag = true;
        String errorMsg = "新增数据:";
        try {
            super.save(entity);
        } catch (Exception e) {
            errorMsg = errorMsg + e.getMessage();
            successFlag = false;
            throw e;
        } finally {
            // 从泛型实体类中,取得MongoDB摘要日志信息

            // 打印MongoDB摘要日志
        }
    }

    /**
     * 批量新增数据
     *
     * @param entitys
     */
    public void saveList(List<T> entitys) {
        try {
            for (T entity : entitys) {
                super.save(entity);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 查询并更新数据（单条）
     */
    @Override
    public T findAndModify(Map<String, Object> queryParams, Map<String, Object> updateParams, Class<T> entityClass) {
        T result;
        long start = System.currentTimeMillis();
        boolean successFlag = true;
        String errorMsg = "查询并更新数据:";
        try {
            result = super.findAndModify(queryParams, updateParams, entityClass);
        } catch (Exception e) {
            errorMsg = errorMsg + e.getMessage();
            successFlag = false;
            throw e;
        } finally {
            // 从查询参数中,取得MongoDB摘要日志信息

            // 打印MongoDB摘要日志
        }
        return result;
    }

    /**
     * 查询单条数据
     */
    @Override
    public T findOne(Map<String, Object> queryParams, Class<T> entityClass) {
        T result;
        long start = System.currentTimeMillis();
        boolean successFlag = true;
        String errorMsg = "查询单条数据:";
        try {
            result = super.findOne(queryParams, entityClass);
        } catch (Exception e) {
            errorMsg = errorMsg + e.getMessage();
            successFlag = false;
            throw e;
        } finally {
            // 从泛型实体类中,取得MongoDB摘要日志信息

            // 打印MongoDB摘要日志
        }
        return result;
    }

    /**
     * 更新数据
     *
     * @param queryParams
     * @param updateParams
     * @param entityClass
     * @return
     */
    public T findByModfiy(Map<String, Object> queryParams, Map<String, Object> updateParams,
                          Class<T> entityClass) {
        return super.findAndModify(queryParams, updateParams, entityClass);
    }

    /**
     * 更新数据
     * 在某个字段中追加更新内容
     *
     * @param queryParams  查询参数
     * @param updateParams 更新参数
     * @param entityClass  实体类
     * @return
     */
    public T findAndModifyForPush(Map<String, Object> queryParams, Map<String, Object> updateParams,
                                  Class<T> entityClass) {
        Query query = this.createQuery(queryParams);
        Update update = this.createUpdate(updateParams);
        return super.getMongoTemplate().findAndModify(query, update, entityClass);
    }

    /**
     * 创建查询语句
     *
     * @param params
     * @return
     */
    private Query createQuery(Map<String, Object> params) {
        Query query = new Query();
        Criteria criteria = null;
        int count = 0;

        for (Iterator<Entry<String, Object>> var5 = params.entrySet().iterator(); var5.hasNext(); ++count) {
            Entry<String, Object> param = var5.next();
            if (count == 0) {
                criteria = Criteria.where(param.getKey()).is(param.getValue());
            } else {
                criteria.and(param.getKey()).is(param.getValue());
            }
        }

        query.addCriteria(criteria);
        return query;
    }

    /**
     * 创建更新语句
     *
     * @param params
     * @return
     */
    private Update createUpdate(Map<String, Object> params) {
        Update update = new Update();
        Iterator<Entry<String, Object>> it = params.entrySet().iterator();

        while (it.hasNext()) {
            Entry<String, Object> param = it.next();
            update.push(param.getKey(), param.getValue());
        }

        return update;
    }

}
