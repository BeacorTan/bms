package com.cl.common.framework.util;

import com.cl.common.model.AuthBase;
import com.github.pagehelper.Page;
import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanUtil {

    public static Map<String, String> ctrlTypeMap = new HashMap<String, String>(10);

    static {
        ctrlTypeMap.put("company", "companies");
        ctrlTypeMap.put("dept", "depts");
        ctrlTypeMap.put("group", "groups");
    }

    public static <T> PagedResult<T> toPagedResult(List<T> datas) {
        PagedResult<T> result = new PagedResult<T>();
        if (datas instanceof Page) {
            Page page = (Page) datas;
            result.setPageNumber(page.getPageNum());
            result.setPageSize(page.getPageSize());
            result.setDataList(page.getResult());
            result.setTotal(page.getTotal());
            result.setPages(page.getPages());
        } else {
            result.setPageNumber(1);
            result.setPageSize(datas.size());
            result.setDataList(datas);
            result.setTotal(datas.size());
        }

        return result;
    }


    public static void setFieldValue(String fieldName, String value, AuthBase authBase) throws NoSuchFieldException, IllegalAccessException {

        Field field = authBase.getClass().getField(ctrlTypeMap.get(fieldName));
        field.setAccessible(true);
        List<String> fieldValue = (List<String>) field.get(authBase);
        if (CollectionUtils.isEmpty(fieldValue)) {
            fieldValue = new ArrayList<String>(10);
        }
        fieldValue.add(value);
        field.set(authBase, fieldValue);
    }

//    public static void main(String[] args) {
//        List<String> lists = null;
//        Object obj = lists;
//        System.out.println((List<String>) obj);
//    }

}
