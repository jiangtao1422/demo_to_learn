package com.jt.controller;

import com.alibaba.fastjson.JSON;
import com.jt.dfa.SensitiveUtils;
import com.jt.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

/**
 * @author jiangtao
 * @create 2022/10/17 9:59
 */
@RestController
@RequestMapping("/json")
@Slf4j
public class JSONController {

    /**
     * @return
     */
    @RequestMapping("test1")
    public Result getDfa() {
        String json = "{\"personnelList\":[{\"name\":0,\"startTime\":\"2022-10-17 09:05\",\"startUrl\":\"http://120.246.61.98:31999/sys/common/static/easydata/20221017/0308d8604ba40ee6b6bf17afd05ff908.png\",\"endTime\":\"2022-10-20 09:05\",\"endUrl\":\"http://120.246.61.98:31999/sys/common/static/easydata/20221017/362727d7fd0ba6866b2c04226d1fb54a.png\"}],\"Tools\":[{\"enterName\":\"ew\",\"enterNum\":\"22\",\"outName\":\"ee\",\"outNum\":\"33\"}],\"msg\":\"eeeeeeeeeeed\",\"fileList1\":[{\"type\":\"image\",\"url\":\"easydata/20221017/85c68afc5c2d35e29cf37e737e50d7cb.jpg\",\"thumb\":\"blob:http://localhost:8080/8031389e-3eea-489c-8b86-981c6fd25731\",\"size\":691450,\"name\":\"c2f38d036b90e18fc6b806a52e35aaa.jpg\",\"status\":\"success\",\"message\":\"\"},{\"type\":\"image\",\"url\":\"easydata/20221017/260905e25d2f3a704a7571035e6ea086.jpg\",\"thumb\":\"blob:http://localhost:8080/ed944d28-8f7d-41af-b56b-9f0527ec4eb1\",\"size\":691450,\"name\":\"c2f38d036b90e18fc6b806a52e35aaa.jpg\",\"status\":\"success\",\"message\":\"\"}],\"time\":\"2022-10-17 09:06\",\"url\":\"http://120.246.61.98:31999/sys/common/static/easydata/20221017/e750b62e55b6e5e541ae1d8a4136f170.png\"}";
        Map parse = (Map) JSON.parse(json);
        Set set = parse.entrySet();
        set.stream().forEach(item ->{
            Map.Entry entry = (Map.Entry) item;
            System.out.println(entry.getKey() + "-" + entry.getValue());
        });
        return Result.OK(parse);

    }

}
