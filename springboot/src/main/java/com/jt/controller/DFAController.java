package com.jt.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jt.dfa.SensitiveUtils;
import com.jt.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author jiangtao
 * @create 2022/10/4 11:35
 */
@RestController
@RequestMapping("/boot")
@Slf4j
public class DFAController {

    /**
     * 获取DFA模型
     *
     * @return
     */
    @RequestMapping("getDfa")
    public Result getDfa() {
        return Result.OK(SensitiveUtils.sensitiveWordMap);

    }

    /**
     * dfa构建
     *
     * @return
     */
    @RequestMapping("dfa")
    public Result dfa() {
        HashSet<String> strings = new HashSet<>();
        strings.add("匹配算法");
        strings.add("匹配关键字");
        strings.add("抽取信息");
        strings.add("抽取脂肪");
        strings.add("脂肪很多");
        Map map = SensitiveUtils.addSensitiveWordToHashMap(strings);
        JSONObject jsonObject = (JSONObject) JSON.toJSON(map);
        return Result.OK(jsonObject);
    }

    /**
     * dfa算法方式
     *
     * @param txt
     * @param matchType
     * @return
     */
    @RequestMapping(value = "check", method = RequestMethod.GET)
    public Result check(@RequestParam String txt, int matchType) {
        SensitiveUtils instance = SensitiveUtils.getInstance();
        Set<String> set = instance.getSensitiveWordSets(txt, matchType);
        return Result.OK(set);
    }

    /**
     * 传统方式
     *
     * @param txt
     * @return
     */
    @RequestMapping(value = "check1", method = RequestMethod.GET)
    public Result check1(@RequestParam String txt) {
        HashSet<String> strings = new HashSet<>();
        strings.add("匹配算法");
        strings.add("匹配关键字");
        strings.add("抽取信息");
        strings.add("抽取脂肪");
        strings.add("脂肪很多");
        HashSet<String> set = new HashSet<>();

        for (Iterator<String> iterator = strings.iterator(); iterator.hasNext(); ) {
            String next = iterator.next();
            if (txt.contains(next)) {
                set.add(next);
            }
        }
        return Result.OK(set);
    }
}
