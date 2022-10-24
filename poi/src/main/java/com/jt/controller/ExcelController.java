package com.jt.controller;

import com.jt.service.ExcelService;
import com.jt.service.PdfService;
import com.jt.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author jiangtao
 * @create 2022/10/2 14:20
 */
@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @Autowired
    private PdfService pdfService;

    @RequestMapping("create")
    public Result createExcel() throws IOException {
        excelService.createExcel();
        return Result.OK("操作成功");
    }



}
