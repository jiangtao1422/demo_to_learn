package com.jt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.jt.entity.UserInfoModel;
import com.jt.service.PdfService;
import com.jt.utils.PdfUtils;
import com.jt.utils.StringUtil;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

/**
 * @author jiangtao
 * @create 2022/10/2 14:16
 */
@Service
public class PdfServiceImpl implements PdfService {

    // excel模板文件
    String modelFile = "personalInformation.xlsx";
    // 签字图片
    String image = "zhangsan.png";
    // 生成的pdf文件
    String filename = "person.pdf";

    /**
     * @param userInfoModel
     * @param response
     */
    @Override
    @SneakyThrows
    public void createPdf(UserInfoModel userInfoModel, HttpServletResponse response) {
        // 将对象转换为map
        Map<String, String> map = JSON.parseObject(JSON.toJSONString(userInfoModel)
                , new TypeReference<Map<String, String>>() {
                });
        // 获取excel模板
        ClassPathResource classPathResource = new ClassPathResource(modelFile);
        InputStream inputStream = classPathResource.getInputStream();
        // 读入创建的模板
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        // 获取第一个sheet
        XSSFSheet sheet = wb.getSheetAt(0);
        // 获取画布，每个sheet只有一个，向excel渲染图片用
        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        for (Iterator ite = sheet.rowIterator(); ite.hasNext(); ) {
            XSSFRow row = (XSSFRow) ite.next();
            // 遍历excel的每个单元格，替换模板中的el表达式${}
            for (Iterator itet = row.cellIterator(); itet.hasNext(); ) {
                XSSFCell cell = (XSSFCell) itet.next();
                if (cell != null) {
                    // 获取单元格中的文本
                    String cellValue = cell.getStringCellValue();
                    // 替换${}
                    String replaceEl = StringUtil.replaceEl(cellValue, map);
                    // 回写替换后的文本到excel单元格
                    cell.setCellValue(replaceEl);
                }
            }
        }
        // 获取用户签字图片
        ClassPathResource classPathResource1 = new ClassPathResource(image);
        InputStream inputStream1 = classPathResource1.getInputStream();
        // 将用户签字图片渲染到excel中
        int picture = wb.addPicture(inputStream1, Workbook.PICTURE_TYPE_PNG);
        XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 5, 6, 5, 6);
        drawing.createPicture(anchor, picture).resize(0.05);
        // 设置响应头并获取响应输出流
        OutputStream outputStream = getOutputStream(response, filename);
        // 将渲染好的excel文件写出响应输出流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        wb.write(byteArrayOutputStream);
        // 将输出流转换为输入流
        InputStream excelInput = outputToinputStream(byteArrayOutputStream);
        // 将生成的excel转换为pdf
        PdfUtils.excelToPdf(excelInput, outputStream);
    }

    /**
     * 将ByteArrayOutputStream转换为inputStream
     *
     * @param byteArrayOutputStream
     * @return
     */
    private InputStream outputToinputStream(ByteArrayOutputStream byteArrayOutputStream) {
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    /**
     * 设置响应头为下载并获取响应输出流
     *
     * @param response
     * @param filename
     * @return
     */
    private OutputStream getOutputStream(HttpServletResponse response, String filename) {
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            // attachment为文件下载
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputStream;
    }
}
