package com.example.reggie.controller;

import com.example.reggie.common.R;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common")
public class CommonController {

    @Value("${reggie.path}")
    private String basePath;
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        //获取原始的文件名
        String originalFilename =file.getOriginalFilename();

//        使用uuid生成文件名

        //截取文件后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        //使用uuid重新生成文件名
        String fileName = UUID.randomUUID().toString()+suffix;

        //文件夹不存在，创建目录
        File dir = new File(basePath);

        //目录不存在创建目录
        if(!dir.exists()){
            dir.mkdirs();
        }

        try {
            file.transferTo(new File(basePath+fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return R.success(fileName);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        try {
            //输入流读取图片
            FileInputStream fileInputStream = new FileInputStream(new File(basePath+name));

            //输出流
            ServletOutputStream outputStream =response.getOutputStream();

            int len = 0 ;
            byte[] bytes = new byte[1024];
            while((len = fileInputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }

            outputStream.close();
            fileInputStream.close();

        } catch (Exception e) {
               e.printStackTrace();
        }


    }
}
