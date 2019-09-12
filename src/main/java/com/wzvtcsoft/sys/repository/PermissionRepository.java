package com.wzvtcsoft.sys.repository;

import com.wzvtcsoft.sys.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission,Long> {
}
