package com.jt.service;


import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * @author jiangtao
 * @create 2022/10/3 20:13
 */
public interface SortedService {

    List<Integer> sortList(List<Integer> intList);

    List<String> sortList(LinkedList<String> strList);

    List<BigDecimal> sortDecList(LinkedList<BigDecimal> decList);

    <T extends Comparable<T>> List<T> sortDecList1(List<T> decList);

}
