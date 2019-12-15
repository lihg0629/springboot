package com.example.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.redis.bean.User;
import com.example.redis.service.UserService;

/**
 * ユーザコントローラー
 * @author lihuaguang
 *
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
    /**
     * IDによってユーザを探す（@PathVariable）
     * @param id
     * @return
     */
    @RequestMapping(value = "/api/findinfo/{id}")
    public User findUserById(@PathVariable("id") Long id) {
        return userService.findUserById(id);
    }
    /**
     * ユーザ名によってユーザを探す（@RequestParam）
     * @param id
     * @return
     */
    @RequestMapping(value = "/api/findinfo")
    public User findUserByName(@RequestParam("userName") String userName) {
        return userService.findUserByName(userName);
    }


//    @RequestMapping(value = "/api/create")
//    public void saveUser(User user) {
//        userService.saveUser(user);
//
//    }

//    @RequestMapping(value = "/api/modify")
//    public void updateUser(User user) {
//        userService.updateUser(user);
//    }
//    @RequestMapping(value = "/api/delete/{id}")
//    public void deleteUser(@PathVariable("id") Long id) {
//        userService.deleteUser(id);
//    }
}
