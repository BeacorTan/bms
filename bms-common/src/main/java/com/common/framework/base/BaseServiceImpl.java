package com.common.framework.base;

import com.common.framework.util.BeanUtil;
import com.common.framework.util.HelpUtils;
import com.common.framework.util.PagedResult;
import com.github.pagehelper.PageHelper;

import java.util.List;

/**
 * 基础接口
 *
 * @date 2016年09月02日
 */
public abstract class BaseServiceImpl<T extends BaseModel> implements BaseService<T> {

    public abstract BaseMapper getMapper();

    @Override
    public T insert(T pojo)  {
        getMapper().insert(pojo);
        return pojo;
    }


    @Override
    public T insertSelective(T pojo)  {
        getMapper().insertSelective(pojo);
        return pojo;
    }

    @Override
    public T updateByPrimaryKey(T pojo)  {
        getMapper().updateByPrimaryKey(pojo);
        return pojo;
    }

    @Override
    public T updateByPrimaryKeySelective(T pojo) {
        getMapper().updateByPrimaryKeySelective(pojo);
        return pojo;
    }

    @Override
    public int delete(T key)  {
        return getMapper().delete(key);
    }

    @Override
    public int deleteByPrimaryKey(Object key)  {
        return getMapper().deleteByPrimaryKey(key);
    }

    @Override
    public boolean deleteByPrimaryKeyList(List<String> keys)  {
        if (HelpUtils.isEmpty(keys)) {//判断是否为空
            return false;
        }
        for (String id : keys) {
            getMapper().deleteByPrimaryKey(id);
        }
        return true;
    }

    @Override
    public boolean updateActiveFlagByPrimaryKeyList(List<String> keys, T t)  {

        if (HelpUtils.isEmpty(keys)) {//判断是否为空
            return false;
        }

        for (String id : keys) {
            t.setId(id);
            getMapper().updateByPrimaryKeySelective(t);
        }
        return true;
    }

    @Override
    public List<T> select(T pojo)  {
        return getMapper().select(pojo);
    }

    @Override
    public int selectCount(T record)  {
        return getMapper().selectCount(record);
    }

    @Override
    public T selectByPrimaryKey(Object key)  {
        T pojo = (T) getMapper().selectByPrimaryKey(key);
        return pojo;
    }

    @Override
    public List<T> selectAll()  {
        return getMapper().selectAll();
    }

    @Override
    public PagedResult<T> findPageList(Integer pageNo, Integer pageSize, T pojo)  {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? 10 : pageSize;
        PageHelper.orderBy("CREATE_DATE desc");//默认都是以时间来排序
        PageHelper.startPage(pageNo, pageSize);  //startPage是告诉拦截器说我要开始分页了。分页参数是这两个。
        return BeanUtil.toPagedResult(getMapper().select(pojo));
    }
}