package com.base.dict.controller;

import com.base.dict.service.IDictService;
import com.base.dict.model.DictVO;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author BoSongsh
 * @create 2018-03-01 17:24
 **/
@RequestMapping("/dict")
@RestController
public class DictController {

    private Logger LOGGER = LoggerFactory.getLogger(DictController.class);

    @Autowired
    private IDictService dictService;


    /**
     * 用户管理主页面
     *
     * @return
     */
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public ModelAndView dictMain() {
        return new ModelAndView("system/dict/dict_list");
    }

    /**
     * 用户管理主页面
     *
     * @return
     */
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView profile(String id, ModelMap modelMap) {
        ModelAndView modelAndView = new ModelAndView("system/dict/dict_profile");
        if (StringUtils.isNotBlank(id)) {
            modelMap.put("dictVO", dictService.selectById(id));
            modelAndView.getModelMap().addAllAttributes(modelMap);
        }
        return modelAndView;
    }


    @RequestMapping(value = "/page/list", method = RequestMethod.GET)
    @ResponseBody
    public PagedResult<DictVO> getDictPageList(PageBean pageBean, DictVO dict) throws Exception {
        return dictService.selectPageList(pageBean, dict);
    }


    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public List<DictVO> query(@RequestBody DictVO dictVO) {
        List<DictVO> queryResult = null;
        if (dictVO == null) {
            LOGGER.error("DictController.query()查询条件为空");
            return queryResult;
        }
        try {
            queryResult = dictService.selectByDictCode(dictVO.getCode());
        } catch (Exception e) {
            LOGGER.error("DictController.query()异常:{}", e);
        }
        return queryResult;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Integer add(@RequestBody DictVO dictVO) {
        Integer result = null;
        try {
            result = dictService.add(dictVO);
        } catch (Exception e) {
            LOGGER.error("DictController.add()异常:{}", e);
        }
        return result;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Integer update(@RequestBody DictVO dictVO) {
        Integer result = null;
        try {
            result = dictService.update(dictVO);
        } catch (Exception e) {
            LOGGER.error("DictController.update()异常:{}", e);
        }
        return result;
    }


    @RequestMapping(value = "/del", method = RequestMethod.GET)
    public Integer update(String id) {
        Integer result = null;
        try {
            result = dictService.delete(id);
        } catch (Exception e) {
            LOGGER.error("DictController.update()异常:{}", e);
        }
        return result;
    }
}
