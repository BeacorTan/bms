package com.base.dict.controller;

import com.base.dict.model.DictDataVO;
import com.base.dict.service.IDictDataService;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;
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

@RequestMapping("/dictData")
@RestController
public class DictDataController {

    private Logger LOGGER = LoggerFactory.getLogger(DictDataController.class);

    @Autowired
    private IDictDataService dictDataService;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public ModelAndView dictMain(String code, ModelMap modelMap) {
        modelMap.put("code", code);
        return new ModelAndView("dict/dict_data_main");
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
        if (StringUtils.isNotBlank(id) && !id.startsWith("add")) {
            modelMap.put("dictVO", dictDataService.selectById(id));
        }
        modelMap.put("tabId", id);
        return new ModelAndView("dict/dict_data_profile", modelMap);
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


    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public ResponseJson editDictData(@RequestBody DictDataVO dictVO) {
        return dictDataService.editDictData(dictVO);
    }


    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseJson remove(@RequestBody List<String> ids) {
        return dictDataService.removeByIds(ids);
    }
}
