package com.jt.controller;

import com.jt.dto.SortedDTO;
import com.jt.service.SortedService;
import com.jt.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author jiangtao
 * @create 2022/10/3 20:02
 */
@RestController
@RequestMapping("/boot")
@Slf4j
public class SortController {

    @Autowired
    private SortedService sortedService;

    /**
     * 对传入Integer的数组进行排序并插入数据库中
     *
     * @param sortedDTO
     * @return
     */
    @PostMapping("sortInt")
    public Result sortInt(@RequestBody SortedDTO sortedDTO) {
        List<Integer> sortedAfterList = sortedService.sortDecList1(sortedDTO.getIntList());
        return Result.OK(sortedAfterList);
    }

    /**
     * 对传入String的数组进行排序并插入数据库中
     *
     * @param sortedDTO
     * @return
     */
    @PostMapping("sortStr")
    public Result sortStr(@RequestBody SortedDTO sortedDTO) {
        List<String> sortedAfterList = sortedService.sortDecList1(sortedDTO.getStrList());
        return Result.OK(sortedAfterList);
    }

    /**
     * 对传入BigDecimal的数组进行排序并插入数据库中
     *
     * @param sortedDTO
     * @return
     */
    @PostMapping("sortDec")
    public Result sortDec(@RequestBody SortedDTO sortedDTO) {
        System.out.println(sortedDTO);
        List<BigDecimal> bigDecimals = sortedService.sortDecList1(sortedDTO.getDecList());
        return Result.OK(bigDecimals);
    }


    /**
     * 泛型
     *
     * @param sortedDTO
     * @return
     */
    @PostMapping("sort")
    public Result sort(@RequestBody SortedDTO sortedDTO) {
        List list = sortedService.sortDecList1(sortedDTO.getList());
        return Result.OK(list);
    }
}
