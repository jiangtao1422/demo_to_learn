package com.jt.service;

import com.jt.entity.UserInfoModel;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;

/**
 * @author jiangtao
 * @create 2022/10/2 14:16
 */
public interface PdfService {

    void createPdf(UserInfoModel userInfoModel, HttpServletResponse response) throws FileNotFoundException;

}
