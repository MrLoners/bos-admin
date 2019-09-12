package com.wzvtcsoft.sys.service.impl;

import com.wzvtcsoft.common.BeanUtil;
import com.wzvtcsoft.sys.entity.Role;
import com.wzvtcsoft.sys.entity.RolePermission;
import com.wzvtcsoft.sys.repository.RoleRepository;
import com.wzvtcsoft.sys.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role getOne(Long id) {
        return roleRepository.getOne(id);
    }

    @Override
    public Role insert(Role role) {
        for (RolePermission rolePermission : role.getRolePermissions()){
            rolePermission.setRole(role);
        }
        return roleRepository.save(role);
    }

    @Override
    public Role update(Role role) {
        Role originalRole = roleRepository.getOne(role.getId());
        BeanUtil.copyProperties(role, originalRole);

        for (RolePermission rolePermission : originalRole.getRolePermissions()){
            if (rolePermission.getRole() == null){
                rolePermission.setRole(originalRole);
            }
        }

        return roleRepository.save(originalRole);
    }

    @Override
    public void delete(Role role) {
        roleRepository.delete(role);
    }

    @Override
    public void deleteAll(List<Role> roles) {
        roleRepository.deleteAll(roles);
    }
}
