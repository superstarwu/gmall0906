package com.atguigu.gmall.manage.web.fastdfsTest;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

public class GmallManageWebApplicationTest {

    public static void main (String[] args) throws Exception{
        ClientGlobal.init("tracker.conf");

        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();

        StorageClient storageClient = new StorageClient(trackerServer,null);

        String[] jpgs = storageClient.upload_file("D:\\Kugou\\b.jpg", "jpg", null);

        for(String jpg : jpgs){
            System.err.println(jpg);
        }
    }
}
