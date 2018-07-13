package com.common.framework.constant;

/**
 * 常量
 */
public final class SystemConstant {

    /**
     * 处理成功
     */
    public static final boolean RESPONSE_SUCCESS = true;

    /**
     * 处理失败
     */
    public static final boolean RESPONSE_ERROR = false;

    /**
     * 新增页面 tab id前缀
     */
    public static final String ADD_VIEW_TAB_ID_PREFIX = "add";

    /**
     * 编辑页面tabid属性名称
     */
    public static final String PROFILE_TAB_ID_ATTRIBUTE_NAME = "tabId";

    public static final String UPDATE_SUCCESS = "编辑成功";

    public static final String UPDATE_ERROR = "编辑失败";

    //public static final String SESSION_FORCE_LOGOUT_KEY = "session.force.logout";

    public final static String ERROR_PAGE = "greeting";
    public final static Integer ERROR_CODE_500 = 500;

//    public static final int CONNECT_TIME_OUT = 60 * 1000;
//    public static final int READ_TIME_OUT = 60 * 1000;

    public static final int CONNECT_TIME_OUT_120 = 120 * 1000;
    public static final int READ_TIME_OUT_120 = 120 * 1000;

    private SystemConstant() {
    }
}
