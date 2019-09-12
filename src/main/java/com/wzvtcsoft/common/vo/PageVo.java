package com.wzvtcsoft.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 封装前段的分页参数，排序参数
 */
@Data
public class PageVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer pageNumber;

    private Integer pageSize;

    private String sort;

    private String order;
}
