package com.common.framework.excel;

import java.util.HashMap;
import java.util.Map;

/**
 * excel表头
 *
 * @author BoSongsh
 * @create 2018-04-19 17:47
 **/
public class ExcelHead {

    public final static Map<String, String> HEADERS_FOLLOW_UP_USER_NO = new HashMap<String, String>(7);

    public final static Map<String, String> HEADERS_FOLLOW_UP_MOBILE = new HashMap(6);


    static {
        HEADERS_FOLLOW_UP_USER_NO.put("userNo", "客户ID");
        HEADERS_FOLLOW_UP_USER_NO.put("customerName", "客户姓名");
        HEADERS_FOLLOW_UP_USER_NO.put("seatNo", "坐席工号");
        HEADERS_FOLLOW_UP_USER_NO.put("content", "跟进内容");
        HEADERS_FOLLOW_UP_USER_NO.put("createDate", "跟进时间");
        HEADERS_FOLLOW_UP_USER_NO.put("status", "状态");

        HEADERS_FOLLOW_UP_MOBILE.put("mobile", "手机");
        HEADERS_FOLLOW_UP_MOBILE.put("customerName", "客户姓名");
        HEADERS_FOLLOW_UP_MOBILE.put("seatNo", "坐席工号");
        HEADERS_FOLLOW_UP_MOBILE.put("content", "跟进内容");
        HEADERS_FOLLOW_UP_MOBILE.put("createDate", "跟进时间");
        HEADERS_FOLLOW_UP_MOBILE.put("status", "状态");
    }

}
