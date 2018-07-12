package com.base.dict.controller;

import com.base.dict.model.DictVO;
import com.base.dict.service.IDictService;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/dict")
@RestController
public class DictController {

//    private Logger LOGGER = LoggerFactory.getLogger(DictController.class);

    @Autowired
    private IDictService dictService;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public ModelAndView dictMain() {
        return new ModelAndView("dict/dict_main");
    }


    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView profile(String id, ModelMap modelMap) {
        if (StringUtils.isNotBlank(id) && !id.startsWith("add")) {
            modelMap.put("dictVO", dictService.selectById(id));
        }
        modelMap.put("tabId", id);
        return new ModelAndView("dict/dict_profile", modelMap);
    }


    @RequestMapping(value = "/page/list", method = RequestMethod.GET)
    @ResponseBody
    public PagedResult<DictVO> getDictPageList(PageBean pageBean, DictVO dict) throws Exception {
        return dictService.selectPageList(pageBean, dict);
    }


    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public List<DictVO> query(@RequestBody DictVO dictVO) {
        String code = dictVO == null?"":dictVO.getCode();
        return dictService.selectByDictCode(code);
    }


    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public ResponseJson editDict(@RequestBody DictVO dictVO) {
        return dictService.editDict(dictVO);
    }


    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseJson remove(@RequestBody List<String> ids) {
        return dictService.removeByIds(ids);
    }
}
