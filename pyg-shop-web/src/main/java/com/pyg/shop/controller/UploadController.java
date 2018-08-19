package com.pyg.shop.controller;

import com.pyg.common.FastDFSClient;
import com.pyg.utils.pygResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;



@RestController
public class UploadController {
    @Value("${FILE_SERVER_URL}")
    private String FILE_SERVER_URL;

    @RequestMapping("/upload")
    public pygResult upload(MultipartFile file)  {
        try {
//        获取文件扩展名
            String filename = file.getOriginalFilename();
            String extName = filename.substring(filename.lastIndexOf(".") + 1);
            //创建client客户端
            FastDFSClient dfsClient = new FastDFSClient("classpath:conf/client.conf");
            //执行上传处理;
            String path = dfsClient.uploadFile(file.getBytes(), extName);
            //拼接url
            String url = FILE_SERVER_URL+path;
            return  new pygResult(true,url);
        } catch (Exception e) {
            e.printStackTrace();
            return new pygResult(false,"上传失败");
        }

    }
}
