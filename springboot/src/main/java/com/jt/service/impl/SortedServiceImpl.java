package com.jt.service.impl;

import com.alibaba.fastjson.JSON;
import com.jt.dao.SortedMapper;
import com.jt.entity.SortedModel;
import com.jt.service.SortedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jiangtao
 * @create 2022/10/3 20:13
 */
@Service
public class SortedServiceImpl implements SortedService {

    @Autowired
    private SortedMapper sortedMapper;

    /**
     * 将给定的Integer集合排序并将排序前后的插入放到数据库中
     *
     * @param sortedBeforeList
     * @return
     */
    @Override
    public List<Integer> sortList(List<Integer> sortedBeforeList) {
        // 对传入的list进行排序
        List<Integer> sortedAfterList = sortedBeforeList.stream()
                .sorted()
                .collect(Collectors.toList());
        // 异步插入将排序前后的list插入数据库中
        insertAsync(JSON.toJSONString(sortedBeforeList), JSON.toJSONString(sortedAfterList));
        // 先将结果返回给用户
        return sortedAfterList;
    }

    /**
     * 将给定的String集合排序并将排序前后的插入放到数据库中
     *
     * @param strList
     * @return
     */
    @Override
    public List<String> sortList(LinkedList<String> strList) {
        // 对传入的list进行排序
        List<String> sortedAfterList = strList.stream()
                .sorted()
                /**可以自定义排序规则*/
                .sorted(new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return o2.compareTo(o1);
                    }
                })
                .collect(Collectors.toList());
        // 异步插入将排序前后的list插入数据库中
        insertAsync(JSON.toJSONString(strList), JSON.toJSONString(sortedAfterList));
        // 先将结果返回给用户
        return sortedAfterList;
    }

    /**
     * 将给定的BigDecimal集合排序并将排序前后的插入放到数据库中
     *
     * @param decList
     * @return
     */
    @Override
    public List<BigDecimal> sortDecList(LinkedList<BigDecimal> decList) {
        // 对传入的list进行排序
        List<BigDecimal> sortedAfterList = decList.stream()
                .sorted()
                .collect(Collectors.toList());
        // 异步插入将排序前后的list插入数据库中
        insertAsync(JSON.toJSONString(decList), JSON.toJSONString(sortedAfterList));
        // 先将结果返回给用户
        return sortedAfterList;
    }

    @Override
    public <T extends Comparable<T>> List<T> sortDecList1(List<T> decList) {
        // 对传入的list进行排序
        return decList.stream()
                .sorted()
                .collect(Collectors.toList());
    }


    /**
     * 异步插库
     *
     * @param sortedBefore
     * @param sortedAfter
     */
    private void insertAsync(String sortedBefore, String sortedAfter) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                SortedModel sortedModel = SortedModel.builder()
                        .sortedBefore(sortedBefore)
                        .sortedAfter(sortedAfter)
                        .build();
                sortedMapper.insert(sortedModel);
            }
        };
        new Thread(runnable).start();
    }
}
