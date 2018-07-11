package com.common.framework.base;


import com.common.framework.util.PagedResult;

import java.util.List;

/**
 * 基础接口
 */

public interface BaseService<T extends BaseModel> {
    /**
     * 增加一个实体,增加所有字段
     *
     * @param pojo
     * @return 返回实体类
     */
    T insert(T pojo) ;

    /**
     * 增加一个实体,只会增加不是null的字段
     *
     * @param pojo
     * @return 返回实体类
     */
    T insertSelective(T pojo);

    /**
     * 根据主键进行更新一个实体类,更新所有字段
     *
     * @param pojo
     * @return 修改成功状态
     */
    T updateByPrimaryKey(T pojo) ;

    /**
     * 根据主键进行更新一个实体类,只会更新不是null的字段
     *
     * @param pojo
     * @return
     */
    T updateByPrimaryKeySelective(T pojo) ;

    /**
     * 根据实体类中字段不为null的条件进行删除,条件全部使用=号and条件
     *
     * @param key
     * @return
     */
    int delete(T key) ;

    /**
     * 通过主键进行删除,这里最多只会删除一条数据
     * 单个字段做主键时,可以直接写主键的值
     * 联合主键时,key可以是实体类,也可以是Map
     *
     * @param key
     * @return
     */
    int deleteByPrimaryKey(Object key) ;

    /**
     * 根据主键的集合批量删除数据
     *
     * @param keys
     * @return 是否删除成功
     */
    boolean deleteByPrimaryKeyList(List<String> keys) ;

    /**
     * 根据实体类不为null的字段进行查询集合,条件全部使用=号and条件
     *
     * @param pojo
     * @return
     */
    List<T> select(T pojo) ;

    /**
     * 根据实体类不为null的字段查询总数,条件全部使用=号and条件
     *
     * @param pojo
     * @return
     */
    int selectCount(T pojo) ;

    /**
     * 根据主键进行查询,必须保证结果唯一
     * 单个字段做主键时,可以直接写主键的值
     * 联合主键时,key可以是实体类,也可以是Map
     *
     * @param key
     * @return
     */
    T selectByPrimaryKey(Object key) ;

    /**
     * 查询所有实体集合
     *
     * @return
     */
    List<T> selectAll() ;

    /**
     * 查询分页
     *
     * @param pageNo
     * @param pageSize
     * @param pojo
     * @return
     */
    PagedResult<T> findPageList(Integer pageNo, Integer pageSize, T pojo) ;


    boolean updateActiveFlagByPrimaryKeyList(List<String> keys, T t) ;

    boolean updateActiveFlagByPrimaryKey(T t) ;

}
