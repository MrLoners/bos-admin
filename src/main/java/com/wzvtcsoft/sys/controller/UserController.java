package com.wzvtcsoft.sys.controller;

import com.wzvtcsoft.common.vo.PageQueryParam;
import com.wzvtcsoft.common.vo.Result;
import com.wzvtcsoft.sys.entity.User;
import com.wzvtcsoft.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/sys/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/list")
    public Result list(@RequestBody PageQueryParam<User> pageQueryParam) {
        Page<User> users = userService.listByCondition(pageQueryParam);
        return Result.ok().put("list",users);
    }

    @PostMapping("/add")
    public Result add(@RequestBody User user) {
        userService.insert(user);
        return Result.ok();
    }

    @PostMapping("/update")
    public Result update(@RequestBody User user) {
        userService.update(user);
        return Result.ok();
    }

    @PostMapping("/del")
    public Result del(@RequestBody List<User> users) {
        userService.deleteAll(users);
        return Result.ok();
    }
}
