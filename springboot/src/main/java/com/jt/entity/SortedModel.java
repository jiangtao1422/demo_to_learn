package com.jt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * @author jiangtao
 * @create 2022/10/3 20:44
 */
@Data
@TableName("sort")
@Builder
public class SortedModel {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String sortedBefore;

    private String sortedAfter;

}
