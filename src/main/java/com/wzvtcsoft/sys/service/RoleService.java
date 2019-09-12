package com.wzvtcsoft.sys.service;

import com.wzvtcsoft.sys.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();

    Role getOne(Long id);

    Role insert(Role role);

    Role update(Role role);

    void delete(Role role);

    void deleteAll(List<Role> roles);
}
