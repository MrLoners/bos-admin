package com.wzvtcsoft.common.util;

import com.wzvtcsoft.common.vo.PageVo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

public class PageUtil {

    public static Pageable initPage(PageVo pageVo){

        int pageNumber = pageVo != null ? pageVo.getPageNumber() : 1;
        int pageSize = pageVo != null ? pageVo.getPageSize() : 10;
        String sort = pageVo != null ? pageVo.getSort() : null;
        String order = pageVo != null ? pageVo.getOrder() : null;

        if (!StringUtils.isEmpty(sort)){
            Sort.Direction d;
            if (!StringUtils.isEmpty(order)){
                d = Sort.Direction.DESC;
            } else {
                d = Sort.Direction.valueOf(order.toUpperCase());
            }
            Sort s = new Sort(d,sort);
            return PageRequest.of(pageNumber - 1, pageSize, s);
        } else {
            return PageRequest.of(pageNumber - 1, pageSize );
        }
    }
}
