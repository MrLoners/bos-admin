package com.wzvtcsoft.sys.service;

import com.wzvtcsoft.sys.entity.Permission;

import java.util.List;

public interface PermissionService {
    List<Permission> findAll();

    Permission getOne(Long id);

    Permission insert(Permission permission);

    Permission update(Permission permission);

    void delete(Permission permission);

    void deleteAll(List<Permission> permissions);
}
