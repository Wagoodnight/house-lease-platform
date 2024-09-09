package com.web.platform.pojo.dto;

import lombok.Data;

/**
 * @author YaeMiko
 */
@Data
public class HouseRentalRequestDTO {
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
}
