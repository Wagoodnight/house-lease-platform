package com.web.platform.pojo.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * @author YaeMiko
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class HouseRentalRequestQuery extends Page{
    /**
     * 查询关键字
     */
    private Long userId;

    private String location;

    private Integer area;

    private Integer room;

    private Integer hall;

    private Integer rest;

    private Integer isKitchen;

    private Integer isBalcony;

    private Integer houseFacing;

    private Float minPrice;

    private Float maxPrice;



    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endTime;
}
