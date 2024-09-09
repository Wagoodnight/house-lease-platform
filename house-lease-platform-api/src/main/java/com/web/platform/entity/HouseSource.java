package com.web.platform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author YaeMiko
 */
@Data
@TableName("t_house_source")
public class HouseSource {
    @TableId(value = "house_id", type = IdType.AUTO)
    private Long houseId;

    private Long userId;

    private String location;

    private Float area;

    private Integer room;

    private Integer hall;

    private Integer rest;

    private Integer isKitchen;

    private Integer isBalcony;

    private Integer houseFacing;

    private String houseBase64;

    private Float price;

    private String remark;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
