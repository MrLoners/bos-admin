package com.wzvtcsoft.common.vo;

import lombok.Data;

/**
 * 包含业务查询参数，分页参数
 */
@Data
public class PageQueryParam<T> {
    /**
     * 业务查询参数
     */
    private T entityParam;

    /**
     * 分页参数
     */
    private PageVo pageParam;
}
