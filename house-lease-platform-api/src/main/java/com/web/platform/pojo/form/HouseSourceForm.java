package com.web.platform.pojo.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author YaeMiko
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HouseSourceForm {
    private Long houseId;

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
