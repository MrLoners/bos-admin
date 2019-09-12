package com.wzvtcsoft.sys.service.impl;


import com.wzvtcsoft.common.BeanUtil;
import com.wzvtcsoft.sys.entity.Permission;
import com.wzvtcsoft.sys.repository.PermissionRepository;
import com.wzvtcsoft.sys.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }

    @Override
    public Permission getOne(Long id) {
        return permissionRepository.getOne(id);
    }

    @Override
    public Permission insert(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Override
    public Permission update(Permission permission) {
        Permission oldPermission = permissionRepository.getOne(permission.getId());
        BeanUtil.copyProperties(permission,oldPermission);
        return permissionRepository.save(oldPermission);
    }

    @Override
    public void delete(Permission permission) {
        permissionRepository.delete(permission);
    }

    @Override
    public void deleteAll(List<Permission> permissions) {
        permissionRepository.deleteAll(permissions);
    }
}
