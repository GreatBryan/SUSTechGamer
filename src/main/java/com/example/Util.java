package com.example;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class Util {
    public boolean httpUpload(MultipartFile files[], String uplpadPath, String fileName){
        for (int i = 0;i < files.length;i++){
            File dest = new File(uplpadPath + "/"+fileName);
            if (!dest.getParentFile().exists()){
                dest.getParentFile().mkdirs();
            }
            try {
                files[i].transferTo(dest);
            }catch (Exception e){
                return false;
            }
        }
        return true;
    }

    public String download(HttpServletResponse response, String path, String filename){
        File g_file = new File(path);
        if (!g_file.exists())
            return "No such file";
        response.reset();
        response.setContentType("application/octst-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) g_file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(g_file));) {
            byte[] buff = new byte[1024];
            OutputStream os = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            return "download fail";
        }
        return "download success";
    }

    public static void zipFiles(File[] srcFiles, File zipFile) {
        // 判断压缩后的文件存在不，不存在则创建
        if (!zipFile.exists()) {
            try {
                zipFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 创建 FileOutputStream 对象
        FileOutputStream fileOutputStream = null;
        // 创建 ZipOutputStream
        ZipOutputStream zipOutputStream = null;
        // 创建 FileInputStream 对象
        FileInputStream fileInputStream = null;
        try {
            // 实例化 FileOutputStream 对象
            fileOutputStream = new FileOutputStream(zipFile);
            // 实例化 ZipOutputStream 对象
            zipOutputStream = new ZipOutputStream(fileOutputStream);
            // 创建 ZipEntry 对象
            ZipEntry zipEntry = null;
            // 遍历源文件数组
            for (int i = 0; i < srcFiles.length; i++) {
                // 将源文件数组中的当前文件读入 FileInputStream 流中
                fileInputStream = new FileInputStream(srcFiles[i]);
                // 实例化 ZipEntry 对象，源文件数组中的当前文件
                zipEntry = new ZipEntry(srcFiles[i].getName());
                zipOutputStream.putNextEntry(zipEntry);
                // 该变量记录每次真正读的字节个数
                int len;
                // 定义每次读取的字节数组
                byte[] buffer = new byte[1024];
                while ((len = fileInputStream.read(buffer)) > 0) {
                    zipOutputStream.write(buffer, 0, len);
                }
            }
            zipOutputStream.closeEntry();
            zipOutputStream.close();
            fileInputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getDes(String pathname) {
        BufferedReader br = null;
        String result = "";
        try {
            br = new BufferedReader(new FileReader(new File(pathname)));
            System.out.println(br.read());
            result = br.readLine();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getEmail(String pathname) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(new File(pathname)));
        String result = br.readLine();
        result = br.readLine();
        return result;
    }
}

