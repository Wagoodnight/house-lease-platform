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
@TableName("t_house_rental_request")
public class HouseRentalRequest {
    @TableId(value = "request_id", type = IdType.AUTO)
    private Long requestId;

    private Long userId;

    private String location;

    private Integer area;

    private Integer room;

    private Integer hall;

    private Integer rest;

    private Integer isKitchen;

    private Integer isBalcony;

    private Integer houseFacing;

    private Float price;

    private String remark;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
