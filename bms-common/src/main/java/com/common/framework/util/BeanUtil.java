package com.common.framework.util;

import com.github.pagehelper.Page;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BeanUtil {

    static ThreadLocalRandom threadLocalRandom=ThreadLocalRandom.current();

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

    public static String getBizId(){
        StringBuilder builder=new StringBuilder();
        builder.append(System.currentTimeMillis());
        builder.append(threadLocalRandom.nextInt(10000,99999));
        return builder.toString();
    }


//
//
//    public static void main(String[] args) {
//        ThreadLocalRandom threadLocalRandom=ThreadLocalRandom.current();
//        System.out.println(threadLocalRandom.nextInt(10000,99999));
//    }

}
