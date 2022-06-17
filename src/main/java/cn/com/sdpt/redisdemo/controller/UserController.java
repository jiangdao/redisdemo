package cn.com.sdpt.redisdemo.controller;

import cn.com.sdpt.redisdemo.entity.User;
import cn.com.sdpt.redisdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/getUser/{uid}")
    public Object getUser(@PathVariable Integer uid){
        ExecutorService es= Executors.newFixedThreadPool(200);
        for (int i=0;i<300;i++){
            es.submit(new Runnable() {
                @Override
                public void run() {
                    userService.getOneUser(uid);
                }
            });
        }

        return userService.getOneUser(uid);
    }
}
