//package com.cl.common.framework.util;
//
//import org.csource.common.NameValuePair;
//import org.csource.fastdfs.*;
//
///**
// * fastdfs的封装后的api
// */
//public class FastDFSClientUtils {
//    private TrackerClient trackerClient = null;
//    private TrackerServer trackerServer = null;
//    private StorageClient1 storageClient = null;
//    private StorageServer storageServer = null;
//
//    public FastDFSClientUtils(String conf) throws Exception {
//        if (conf.contains("classpath:")) {
//            conf = conf.replace("classpath:", this.getClass().getResource("/").getPath());
//        }
//        ClientGlobal.init(conf);
//        trackerClient = new TrackerClient();
//        trackerServer = trackerClient.getConnection();
//        storageServer = null;
//        storageClient = new StorageClient1(trackerServer, storageServer);
//
//    }
//
//    public String uploadFile(String fileName, String extName, NameValuePair[] metas) throws Exception {
//        String result = storageClient.upload_file1(fileName, extName, metas);
//        return result;
//    }
//    public String uploadFile(byte[] file_buff, String file_ext_name, NameValuePair[] meta_list) throws Exception {
//        String result = storageClient.upload_file1(file_buff, file_ext_name, meta_list);
//        return result;
//    }
//
//    public String uploadFile(String fileName) throws Exception {
//        String result = storageClient.upload_file1(fileName, null, null);
//        return result;
//    }
//
//    public String uploadFile(String fileName, String extName) throws Exception {
//        String result = storageClient.upload_file1(fileName, extName, null);
//        return result;
//    }
//    /*
//    * 文件删除的方法
//    * */
//    public int deleteFile(String groupName,String path)throws  Exception {
//        return  storageClient.delete_file(groupName, path);
//    }
//    /*
//    * 文件下载的方法
//    * */
//    public byte[] downloadFile(String groupName,String path) throws Exception{
//        return storageClient.download_file(groupName,path);
//    }
//}
