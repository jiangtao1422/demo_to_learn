package com.jt;


import java.util.Objects;

/**
 * @author jiangtao
 * @create 2022/10/16 16:47
 */
public class Main2 {
    public static void main(String[] args) {

        boolean equals = Objects.equals(null, null);
        System.out.println(equals);
        int hash = Objects.hash("a", "b");
        System.out.println(hash);
    }
}
