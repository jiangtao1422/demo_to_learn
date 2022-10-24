package com.jt.dfa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author jiangtao
 * @create 2022/10/4 11:34
 */
@Slf4j
@Component
public class SensitiveUtils {

    public static HashMap sensitiveWordMap;

    /**
     * 私有静态变量
     */
    private volatile static SensitiveUtils instance;

    static {
        HashSet<String> strings = new HashSet<>();
        strings.add("我是张三");
        strings.add("我是李四");
        strings.add("大王八");
        strings.add("大王来了");
        addSensitiveWordToHashMap(strings);
    }

    /**
     * 获取实例（双重检测锁）
     *
     * @return SensitiveWordFilter
     */
    public static SensitiveUtils getInstance() {
        if (instance == null) {
            synchronized (SensitiveUtils.class) {
                if (instance == null) {
                    instance = new SensitiveUtils();
                }
            }
        }
        return instance;
    }


    public static Map addSensitiveWordToHashMap(Set<String> keyWordSet) {
        // 初始化HashMap对象并控制容器的大小
        sensitiveWordMap = new HashMap(keyWordSet.size());
        // 敏感词
        String key = null;
        // 用来按照相应的格式保存敏感词库数据
        Map nowMap = null;
        // 用来辅助构建敏感词库
        Map<String, Boolean> newWorMap = null;
        // 使用一个迭代器来循环敏感词集合
        Iterator<String> iterator = keyWordSet.iterator();
        while (iterator.hasNext()) {

            key = iterator.next();
            nowMap = sensitiveWordMap;
            for (int i = 0; i < key.length(); i++) {

                // 截取敏感词当中的字，在敏感词库中字为HashMap对象的Key键值
                char keyChar = key.charAt(i);

                // 判断这个字是否存在于敏感词库中
                Object wordMap = nowMap.get(keyChar);
                if (wordMap != null) {

                    nowMap = (Map) wordMap;
                } else {

                    newWorMap = new HashMap<>();
                    newWorMap.put("isEnd", false);
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                }
                // 如果该字是当前敏感词的最后一个字，则标识为结尾字
                if (i == key.length() - 1) {
                    nowMap.put("isEnd", true);
                }

            }

        }
        log.info("敏感词初始化完毕！");
        return sensitiveWordMap;
    }

    /**
     * 检查文字中是否包含敏感字符，检查规则如下
     *
     * @param txt 目标文本
     * @return int 如果存在，则返回敏感词字符的长度，不存在返回0
     */
    public int checkSensitiveWord(String txt, int matchType) {
        Set<String> sets = getSensitiveWordSets(txt, matchType);
        return sets.size();
    }

    public Set<String> getSensitiveWordSets(String txt, int matchType) {
        Set<String> sensitiveWordSets = new HashSet<>();
        for (int n = 0; n < txt.length(); n++) {
            // 判断是否包含敏感字符
            int length = judgeSensitiveWithIndex(txt, n, matchType);
            if (length > 0) {
                // 存在,加入list中
                sensitiveWordSets.add(txt.substring(n, n + length));
                // 减1的原因，是因为for会自增
                n = n + length - 1;
            }
        }
        return sensitiveWordSets;
    }

    /**
     * 根据指定位置是否是敏感词的开始
     *
     * @param txt        文本
     * @param beginIndex 开始位置
     * @param matchType  匹配模式  1、最小匹配模式  2、最大匹配模式
     * @return int
     */
    private int judgeSensitiveWithIndex(String txt, int beginIndex, int matchType) {
        // 匹配标识数默认为0
        int matchFlag = 0;
        char word;
        boolean isEnd = false;
        Map nowMap = sensitiveWordMap;
        for (int i = beginIndex; i < txt.length(); i++) {
            word = txt.charAt(i);
            // 获取指定key
            nowMap = (Map) nowMap.get(word);
            // 存在，则判断是否为最后一个
            if (nowMap != null) {
                // 找到相应key，匹配标识+1
                matchFlag++;
                if ((boolean) nowMap.get("isEnd")) {
                    // 如果为最后一个匹配规则,结束循环，返回匹配标识数
                    isEnd = true;
                    break;
                }
            } else {
                // 不存在，直接返回
                break;
            }
        }

        if (1 == matchType) {
            // 最小匹配模式
            return matchFlag;
        }
        // 最大匹配模式
        if (isEnd) {
            return matchFlag;
        }
        return 0;
    }
}
