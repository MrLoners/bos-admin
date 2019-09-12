package com.wzvtcsoft.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Data
@Entity(name = "sys_role")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rolename;

    private String remark;

    @OneToMany(mappedBy = "role",cascade = CascadeType.REMOVE,orphanRemoval = true)
    @JsonIgnore
    private Set<UserRole> userRoles;

    @OneToMany(mappedBy = "role",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<RolePermission> rolePermissions;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
