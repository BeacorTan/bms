package com.cl.common.framework.util;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;

/**
 * 业务共用类
 */
public class ServiceUtil {

    public static ResponseJson getResponseJson(String msg, boolean isSuccess) {
        return getResponseJson(msg, isSuccess, null);
    }


    public static void startPage(PageBean pageBean) {
        if (pageBean == null) {
            pageBean = new PageBean();
        }
        Integer pageNo = pageBean.getPageNumber();
        Integer pageSize = pageBean.getPageSize();
        pageNo = (pageNo == null) ? 1 : pageNo;
        pageSize = (pageSize == null) ? 10 : pageSize;
        PageHelper.startPage(pageNo, pageSize);
    }



    /**
     * 获取返回json格式的数据到页面
     *
     * @param msg
     * @param isSuccess
     * @param obj
     * @return
     */
    public static ResponseJson getResponseJson(String msg, boolean isSuccess, Object obj) {
        ResponseJson responseJson = new ResponseJson();
        responseJson.setSuccess(isSuccess);
        responseJson.setMsg(msg);
        responseJson.setObj(obj);
        return responseJson;
    }

    public static JSONObject getValidJson(boolean isValid) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("valid", isValid);
        return jsonObject;
    }

    /**
     * 从起始位置开始截取
     *
     * @param str
     * @param limitLength
     * @return
     */
    public static String limitStringLength(String str, int limitLength) {
        if (!StringUtils.isEmpty(str) && str.length() > limitLength) {
            str = str.substring(0, limitLength);
        }
        return str;
    }

    public static String object2Json(Object o) {
        if (o == null) {
            return "";
        }

        String s = new JsonMapper().toJson(o);
        return s;
    }

    public static <T> T json2Object(String json, Class<T> c) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        T s = new JsonMapper().fromJson(json, c);
        return s;
    }
}
