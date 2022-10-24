package com.jt.utils;

import com.spire.xls.FileFormat;
import com.spire.xls.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author jiangtao
 * @create 2022/7/5 23:27
 */

public class PdfUtils {

    /**
     * 将目标xlsx文件生成pdf
     *
     * @param source 目标xlsx文件
     * @param target 生成的pdf文件
     */
    public static void excelToPdf(String source, String target) {
        Workbook workbook = new Workbook();
        workbook.getConverterSetting().setSheetFitToWidth(true);
        workbook.loadFromFile(source);
        workbook.saveToFile(target, FileFormat.PDF);

    }

    /**
     * 将目标xlsx文件生成pdf
     */
    public static void excelToPdf(InputStream inputStream, OutputStream outputStream) throws IOException {
        Workbook workbook = new Workbook();
        workbook.getConverterSetting().setSheetFitToWidth(true);
        workbook.loadFromStream(inputStream);
        workbook.saveToStream(outputStream, FileFormat.PDF);

    }


//    public static void pdfToImage(String pdfSourcePath, String imageTargetPath) {
//        FileInputStream is = null;
//        FileOutputStream outputStream = null;
//        PDDocument doc = null;
//        try {
//            // 获取ftp的文件字节流
//            is = new FileInputStream(pdfSourcePath);
//            // 输出图片
//            outputStream = new FileOutputStream(imageTargetPath);
//            // pdf转图片
//            doc = PDDocument.load(is);
//            PDFRenderer renderer = new PDFRenderer(doc);
//            int pageCount = doc.getNumberOfPages();
//            for (int i = 0; i < pageCount; i++) {
//                // dpi，图片像素点，dpi越高图片体积越大，216很清晰，105体积稳定
//                BufferedImage image = renderer.renderImageWithDPI(i, 216);
//                // 格式为JPG
//                ImageIO.write(image, "jpg", outputStream);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (is != null) {
//                try {
//                    is.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (doc != null) {
//                try {
//                    doc.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (outputStream != null) {
//                try {
//                    outputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }
//    }
}
