package com.atguigu.gmall.manage.web.Util;

import com.atguigu.gmall.ApiUtil;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;

public class ManageUtil {

    public static String getImgUrl(MultipartFile multipartFile){

        String imgUrl = ApiUtil.IMGURL1;
        try {
            ClientGlobal.init("tracker.conf");
        } catch (Exception e) {
            e.printStackTrace();
        }

        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = null;
        try {
            trackerServer = trackerClient.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        StorageClient storageClient = new StorageClient(trackerServer,null);
        String originalName = multipartFile.getOriginalFilename();
        int i = originalName.lastIndexOf(".");
        String subString = originalName.substring(i + 1);


        String[] jpgs = new String[0];
        try {
            jpgs = storageClient.upload_file(multipartFile.getBytes(), subString, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(String jpg : jpgs){
            imgUrl = imgUrl + "/" + jpg;
        }
        return imgUrl;
    }
}
