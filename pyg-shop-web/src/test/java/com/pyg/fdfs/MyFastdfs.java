package com.pyg.fdfs;

import org.csource.fastdfs.*;
import org.junit.Test;

public class MyFastdfs {
    @Test
    public void uploadPic() throws Exception {
        //指定图片绝对地址
        String pic = "D:\\pic\\lang.jpg";
        //指定客户端配置文件绝对地址
        String conf="D:\\IDEA\\PYG85\\pyg-shop-web\\src\\main\\resources\\conf\\client.conf";
        //加载客户端配置文件,连接fastdfs图片服务器
        ClientGlobal.init(conf);
        //创建tracker调度客户端对象
        TrackerClient tc = new TrackerClient();
        TrackerServer trackerServer = tc.getConnection();
        StorageServer storageServer = null;
        //创建存储服务器客户端对象
        StorageClient sc = new StorageClient(trackerServer,storageServer);
        String[] uploadFile = sc.upload_file(pic, "jpg", null);
        for (String url : uploadFile) {
            System.out.println(url);
        }
    }
}
