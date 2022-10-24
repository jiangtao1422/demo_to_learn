package com.jt.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * @author jiangtao
 * @create 2022/10/3 21:10
 */
@Data
public class SortedDTO {

    private LinkedList<Integer> intList;

    private LinkedList<String> strList;

    private LinkedList<BigDecimal> decList;

    private List list;

}
