package cn.com.sdpt.redisdemo.service;

import cn.com.sdpt.redisdemo.entity.User;
import cn.com.sdpt.redisdemo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    public Object getOneUser(Integer uid){
        String key= "user"+uid;
       Object userObj= redisTemplate.opsForValue().get(key);
       if(userObj==null){
           synchronized(this.getClass()){
               userObj= redisTemplate.opsForValue().get(key);
               if(userObj==null){
                   System.out.println("同步代码里面查询数据库.......");
                   userObj= userMapper.selectById(uid);
                   redisTemplate.opsForValue().set(key,userObj);
                   return userObj;
               }
               else{
                   System.out.println("同步代码里面查询缓存.......");
                   return userObj;
               }

           }

       }
       else{
           System.out.println("从缓存获取数据》》》》》》》》");
       }
        return userObj;
    }
}
