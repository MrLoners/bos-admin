package com.wzvtcsoft.sys.controller;

import com.wzvtcsoft.common.vo.Result;
import com.wzvtcsoft.sys.entity.Permission;
import com.wzvtcsoft.sys.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sys/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @GetMapping("/list")
    public Result list() {
        List<Permission>  permissions= permissionService.findAll();
        return Result.ok().put("list",permissions);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Permission permission) {
        permissionService.insert(permission);
        return Result.ok();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Permission permission) {
        permissionService.update(permission);
        return Result.ok();
    }

    @PostMapping("/del")
    public Result del(@RequestBody List<Permission> permissions) {
        permissionService.deleteAll(permissions);
        return Result.ok();
    }
}
