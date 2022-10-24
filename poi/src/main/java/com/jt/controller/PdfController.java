package com.jt.controller;

import com.jt.entity.UserInfoModel;
import com.jt.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;

/**
 * @author jiangtao
 * @create 2022/10/2 14:14
 */
@RestController
@RequestMapping("/pdf")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @PostMapping("create")
    public void create(@RequestBody UserInfoModel userInfoModel, HttpServletResponse response) throws FileNotFoundException {
        pdfService.createPdf(userInfoModel, response);
    }
}
