package com.jt.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangtao
 * @create 2022/10/2 17:33
 */
@Slf4j
public class StringUtil {

    /**
     * 将字符串的${} 转换成map中的字符串，若在map中找不到该字段，则替换为空字符串
     *
     * @param string
     * @param map
     * @return
     */
    public static String replaceEl(String string, Map map) {
        StringSubstitutor stringSubstitutor = new StringSubstitutor(map);
        String replace = stringSubstitutor.replace(string);
        HashMap<String, String> hashMap = new HashMap<>();
        while (StringUtils.isNotBlank(replace) && replace.contains("${")) {
            String substring = replace.substring(replace.indexOf("{") + 1, replace.indexOf("}", 2));
            log.info("找不到{}，替换为空字符串", substring);
            hashMap.put(substring, "");
            stringSubstitutor = new StringSubstitutor(hashMap);
            replace = stringSubstitutor.replace(replace);
        }
        return replace;
    }
}
