package com.jt;

import com.jt.service.ExcelService;
import com.jt.service.PdfService;
import org.apache.commons.text.StringSubstitutor;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author jiangtao
 * @create 2022/10/2 17:19
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {

    @Autowired
    private ExcelService excelService;

    @Autowired
    private PdfService pdfService;

    /**
     * 测试将${}转换字符串方法
     */
    @org.junit.Test
    public void test1() {
        String targetString = "我叫${name}";
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "张三");
        StringSubstitutor stringSubstitutor = new StringSubstitutor(map);
        String replace = stringSubstitutor.replace(targetString);
        System.out.println(replace);
    }

    @org.junit.Test
    public void test2() throws IOException {
        excelService.createExcel();
    }

//    @org.junit.Test
//    public void test3() throws FileNotFoundException {
//        pdfService.createPdf(userInfoModel, response);
//    }
}
