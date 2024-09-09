package com.web.platform.pojo.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author YaeMiko
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HouseRentalRequestForm {
    private Long requestId;

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
