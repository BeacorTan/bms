package com.common.controller;

import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.model.SystemSession;
import com.common.service.SessionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * session管理
 */
@RestController
@RequestMapping(value = "session")
public class SessionController {

    @Resource
    private SessionService sessionService;

    @RequestMapping(value = "/view")
    public ModelAndView sessionListView() {
        return new ModelAndView("system/session/session_list");
    }


    @RequestMapping(value = "/page/list")
    public PagedResult<SystemSession> sessionList(PageBean pageBean, SystemSession systemSession) throws Exception {
        return sessionService.findPageList(pageBean.getPageNumber(), pageBean.getPageSize(), systemSession);
    }
}
