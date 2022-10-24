package com.jt;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author jiangtao
 * @create 2022/10/3 12:51
 */
public class Main {
    public static void main(String[] args) {
//        int[] ints = {3, 5, 7, 9, 13, 11, 6, 6, 3, 3, 3};
//        Map<Integer, Integer> integerIntegerMap = stockPairs(ints, 12);
//        integerIntegerMap.forEach((key, value) -> {
//            System.out.println(key + "-" + value);
//        });
//        System.out.println(integerIntegerMap.size());

        String[] logs = {"30 99 sign-in", "30 105 sign-out", "12 100 sign-in", "20 80 sign-in", "12 120 sign-out", "20 101 sign-out", "21 110 sign-in"};

        String[] strings = processLogs(logs, 20);
        Arrays.asList(strings).forEach(System.out::println);
    }

    /**
     * 获取目标参数(正数)的最小约数，若目标参数为质数，则返回1
     *
     * @param n 目标参数
     * @return 最小约数
     */
    private static int isPrime(long n) {
        int minDivisor = 0;
        boolean isPrime = false;
        for (int i = 1; i <= n; i++) {
            if (n % i == 0 && i != 1 && n != i) {
                // 存在约数，不为质数
                isPrime = true;
                minDivisor = i;
                System.out.println("最小约数" + minDivisor);
                break;
            }
        }
        if (!isPrime) {
            // 质数
            return 1;
        }
        // 最小约数
        return minDivisor;
    }


    /**
     * 计算不重复配对
     * 5 7 9 13 11 6 6 3 3
     * 12
     *
     * @param stocksProfit
     * @param target
     * @return
     */
    private static Map<Integer, Integer> stockPairs(int[] stocksProfit, int target) {
        List<Integer> list = Arrays.stream(stocksProfit)
                .boxed()
                .sorted()
                .collect(Collectors.toList());
        return getMap(list, target);
    }

    /**
     * 获取map，利用选择排序算法思想
     *
     * @param list
     * @param target
     * @return
     */
    private static Map<Integer, Integer> getMap(List<Integer> list, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                int var1 = list.get(i);
                int var2 = list.get(j);
                if (target == var1 + var2) {
                    // 一组
                    map.put(var1, var2);
                }
            }
        }
        return map;
    }


    /**
     * 获取登录登出时差小于maxSpan的用户id数组
     *
     * @param logs
     * @param maxSpan
     * @return
     */
    private static String[] processLogs(String[] logs, int maxSpan) {
        HashMap<String, Map<String, Integer>> map = new HashMap<>();
        Arrays.asList(logs).forEach(item -> {
            // 根据空格进行分割字符串
            String[] split = item.split(" ");
            // user_id
            String userId = split[0];
            // timestamp
            Integer timestamp = Integer.valueOf(split[1]);
            // action
            String action = split[2];
            Map<String, Integer> varMap1 = map.get(userId);
            if (varMap1 == null) {
                varMap1 = new HashMap<>();
            }
            varMap1.put(action, timestamp);
            map.put(userId, varMap1);
        });


        // 开始获取登录登出时差小于等于20秒的用户
        return new ArrayList<>(map.keySet()).stream()
                .filter(item -> {
                    Map<String, Integer> varMap2 = map.get(item);
                    Integer inTime = varMap2.get("sign-in");
                    Integer outTime = varMap2.get("sign-out");
                    return inTime != null && outTime != null && maxSpan >= outTime - inTime;
                })
                .sorted(new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        Map<String, Integer> user1 = map.get(o1);
                        Map<String, Integer> user2 = map.get(o2);
                        Integer inTime1 = user1.get("sign-in");
                        Integer outTime1 = user1.get("sign-out");
                        Integer time1 = outTime1 - inTime1;

                        Integer inTime2 = user2.get("sign-in");
                        Integer outTime2 = user2.get("sign-out");
                        Integer time2 = outTime2 - inTime2;

                        return time2 - time1;
                    }
                })
                .toArray(String[]::new);
    }
}
