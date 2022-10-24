package com.jt.utils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @author jiangtao
 * @create 2022/10/19 22:23
 */
public class FileUtils {

    public static void downloadFile(File file, HttpServletResponse response) {
        String filePath = file.getPath();
        String filename = file.getName();
        ServletOutputStream outputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            outputStream = response.getOutputStream();
            // inline为预览
            response.addHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(filename, "UTF-8"));
            // attachment为文件下载
//            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            // 告知浏览器文件的大小
            response.addHeader("Content-Length", "" + file.length());
            // 如果设置此请求头，则为下载
//            response.setContentType("application/octet-stream");
            bufferedInputStream = new BufferedInputStream(new FileInputStream(filePath));
            byte[] bytes = new byte[1024 * 1024];
            int b = 0;
            while ((b = bufferedInputStream.read(bytes)) != -1) {
                outputStream.write(bytes);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedInputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
