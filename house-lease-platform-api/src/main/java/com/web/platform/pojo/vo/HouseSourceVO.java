package com.web.platform.pojo.vo;

import lombok.Data;

/**
 * @author YaeMiko
 */
@Data
public class HouseSourceVO {
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
}
