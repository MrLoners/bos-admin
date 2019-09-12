package com.wzvtcsoft.sys.controller;

import com.wzvtcsoft.common.vo.Result;
import com.wzvtcsoft.sys.entity.Role;
import com.wzvtcsoft.sys.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sys/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public Result list() {
        List<Role> roles = roleService.findAll();
        return Result.ok().put("list",roles);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Role role) {
        roleService.insert(role);
        return Result.ok();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Role role) {
        roleService.update(role);
        return Result.ok();
    }

    @PostMapping("/del")
    public Result del(@RequestBody List<Role> roles) {
        roleService.deleteAll(roles);
        return Result.ok();
    }
}
