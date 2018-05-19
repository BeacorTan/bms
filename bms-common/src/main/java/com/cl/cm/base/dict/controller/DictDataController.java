package com.cl.cm.base.dict.controller;

import com.cl.cm.base.dict.model.DictDataVO;
import com.cl.cm.base.dict.service.IDictDataService;
import com.cl.common.framework.util.PageBean;
import com.cl.common.framework.util.PagedResult;
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
@RequestMapping("/dictData")
@RestController
public class DictDataController {

    private Logger LOGGER = LoggerFactory.getLogger(DictDataController.class);

    @Autowired
    private IDictDataService dictDataService;


    /**
     * 用户管理主页面
     *
     * @return
     */
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public ModelAndView dictMain(String code, ModelMap modelMap) {
        modelMap.put("code", code);
        return new ModelAndView("system/dict/dict_data_list");
    }


    @RequestMapping(value = "/page/list", method = RequestMethod.GET)
    @ResponseBody
    public PagedResult<DictDataVO> getDictPageList(PageBean pageBean, DictDataVO dict) throws Exception {
        return dictDataService.selectPageList(pageBean, dict);
    }


    /**
     * @return
     */
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView profile(String id, String dictCode, ModelMap modelMap) {
        ModelAndView modelAndView = new ModelAndView("system/dict/dict_data_profile");
        if (StringUtils.isNotBlank(id)) {
            modelMap.put("dictVO", dictDataService.selectById(id));
            modelAndView.getModelMap().addAllAttributes(modelMap);
        } else {
            modelMap.put("dictCode", dictCode);
            modelAndView.getModelMap().addAllAttributes(modelMap);
        }
        return modelAndView;
    }


    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public List<DictDataVO> query(@RequestBody DictDataVO dictVO) {
        List<DictDataVO> queryResult = null;
        if (dictVO == null) {
            LOGGER.error("DictDataController.query()查询条件为空");
            return queryResult;
        }
        try {
            queryResult = dictDataService.selectByDictCode(dictVO.getCode());
        } catch (Exception e) {
            LOGGER.error("DictDataController.query()异常:{}", e);
        }
        return queryResult;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Integer add(@RequestBody DictDataVO dictVO) {
        Integer result = null;
        try {
            result = dictDataService.add(dictVO);
        } catch (Exception e) {
            LOGGER.error("DictDataController.add()异常:{}", e);
        }
        return result;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Integer update(@RequestBody DictDataVO dictVO) {
        Integer result = null;
        try {
            result = dictDataService.update(dictVO);
        } catch (Exception e) {
            LOGGER.error("DictDataController.update()异常:{}", e);
        }
        return result;
    }


    @RequestMapping(value = "/del", method = RequestMethod.GET)
    public Integer update(String id) {
        Integer result = null;
        try {
            result = dictDataService.delete(id);
        } catch (Exception e) {
            LOGGER.error("DictDataController.update()异常:{}", e);
        }
        return result;
    }
}
