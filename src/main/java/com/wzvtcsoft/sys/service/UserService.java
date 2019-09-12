package com.wzvtcsoft.sys.service;

import com.wzvtcsoft.common.vo.PageQueryParam;
import com.wzvtcsoft.sys.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    Page<User> listByCondition(PageQueryParam<User> pageQueryParam);

    List<User> findAll();

    User getOne(Long id);

    User insert(User user);

    User update(User user);

    void delete(User user);

    void deleteAll(List<User> users);

}
