package com.jt;

import java.math.BigDecimal;

/**
 * @author jiangtao
 * @create 2022/10/3 19:35
 */
public class Main1 {
    public static void main(String[] args) {
        BigDecimal bigDecimal = new BigDecimal("0.1");
        BigDecimal decimal = new BigDecimal("0.2");
        BigDecimal add = bigDecimal.add(decimal);
        System.out.println(add);
        System.out.println(0.1 + 0.2);
        System.out.println(1.0 + 2.0);

    }
}
