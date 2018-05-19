package com.cl.common.controller;

import com.cl.common.framework.util.HelpUtils;
import com.cl.common.framework.util.ServiceUtil;
import com.cl.common.model.SysFile;
import com.cl.common.service.SysFileService;
import com.cl.common.shiro.ShiroManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * 系统文件上传控制器
 *
 *
 * @date 2017年04月01日
 */
@Controller
@RequestMapping(value = "/fastdfs")
public class SysFileController {
    @Resource
    private SysFileService sysFileService;
    /**
     * 上传头像
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/upload/photo")
    @ResponseBody
    public Object uploadFile(@RequestParam(value="file") MultipartFile file) throws Exception {
        if (HelpUtils.isNotEmpty(file) && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();//获取文件的名字
            String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);//获取文件后缀名
            String type = file.getContentType();  //文件MIMETYPE类型
            long size = file.getSize();  //获取文件大小
            String path = fastdfsUploadFile(file.getBytes(),prefix);
            SysFile sysFile = new SysFile();
            sysFile.setParentId(ShiroManager.getUserInfo().getId());
            List<SysFile> sysFileList = sysFileService.select(sysFile);//查询是否已经存在照片
            if(HelpUtils.isNotEmpty(sysFileList)){//已经存在,修改照片
                SysFile resultFile = sysFileList.get(0);
                resultFile.setExt(prefix);
                resultFile.setSize(size);
                resultFile.setFilePath(path);
                resultFile.setType(type);
                resultFile.setName(fileName);
                resultFile.setCreateDate(new Date());
                resultFile.setUpdateBy(ShiroManager.getLoginName());
                resultFile.setUpdateDate(new Date());
                sysFileService.updateByPrimaryKeySelective(resultFile);
            }else{
                sysFile.setId(HelpUtils.getUUID());
                sysFile.setActiveFlag(1);
                sysFile.setExt(prefix);
                sysFile.setSize(size);
                sysFile.setFilePath(path);
                sysFile.setType(type);
                sysFile.setName(fileName);
                sysFile.setCreateDate(new Date());
                sysFile.setParentId(ShiroManager.getUserInfo().getId());//登录人
                sysFile.setCreateBy(ShiroManager.getLoginName());
                sysFileService.insertSelective(sysFile);
            }
            return ServiceUtil.getResponseJson("上传成功",true,null);
        } else {
            return ServiceUtil.getResponseJson("上传失败,附件为空",false,null);
        }
    }

    /**
     * 调用Fastdfsapi进行上传文件
     * @param file_buff
     * @param file_ext_name
     * @return
     * @throws Exception
     */
    public String fastdfsUploadFile(byte[] file_buff, String file_ext_name) throws Exception{
//        FastDFSClientUtils client = new FastDFSClientUtils("classpath:config/client.conf");
//        String path = client.uploadFile(file_buff, file_ext_name, (NameValuePair[])null);
        return null;
    }
}
