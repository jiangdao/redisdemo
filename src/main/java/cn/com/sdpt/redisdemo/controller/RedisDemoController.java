package cn.com.sdpt.redisdemo.controller;

import cn.com.sdpt.redisdemo.entity.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
public class RedisDemoController {
    @Resource
    private RedisTemplate<Object,Object> redisTemplate;
    @RequestMapping("/setAndGetString")
    public String setAndGetString(){
        String name=(String)redisTemplate.opsForValue().get("name");
        if(name==null){
            System.out.println("写入缓存........");
            redisTemplate.opsForValue().set("name","zhangsan");
            redisTemplate.expire("name",50, TimeUnit.SECONDS);
        }
        else{
            System.out.println("从缓存取出数据..........");

        }
        return name;

    }

    /**
     * 设置保存map类型到redis
     */
    @RequestMapping(value = "setAndGetMap")
    public Map setAndGetMap(){
        //获取userInfo的map值
        Map<String,Object> map = (Map)redisTemplate.opsForHash().entries("userInfo");
        if(map==null) {
            //设置值
            map = new HashMap<>();
            map.put("name", "张三");
            map.put("age", "18");
            map.put("gender", "男");
            //写入缓存
            System.out.println("写入redis缓存");
            redisTemplate.opsForHash().putAll("userInfo",map);
            //redis中map再加入一对键值
            redisTemplate.opsForHash().put("userInfo","sel","111");
        }
        else{
            //读取redis缓存值
            //读取所有的键（为数组）
            System.out.println("读取redis中缓存的值.....");
            System.out.println(redisTemplate.opsForHash().keys("userInfo"));
            //读取所有的值（为数组）
            System.out.println(redisTemplate.opsForHash().values("userInfo"));
            //读取指定键的值
            System.out.println(redisTemplate.opsForHash().get("userInfo","name"));
        }

        return map;
    }

    /**
     * 设置保存list类型到redis
     */
    @RequestMapping(value = "setAndGetList")
    public List setAndGetList(){

        List leftlist = (List)redisTemplate.opsForList().range("leftList",0,0);
        if(leftlist.size()==0){
            System.out.println("list写入缓存....");
            List list1 = new ArrayList<>();
            list1.add("hello1");
            list1.add("world1");
            User user= new User();
            user.setUsername("abc").setEmail("aa@s.com").setPassword("1234");
            list1.add(user);
            //从集合左边插入值
            redisTemplate.opsForList().leftPush("leftList",list1);
            leftlist.add(list1);
        }
        else{
            System.out.println("从缓存读取list数据");
        }


        List<String > list2 = new ArrayList<>();
        list2.add("hello2");
        list2.add("world2");
        List list3 = new ArrayList<>();
        LocalDateTime datetime1=LocalDateTime.now();
        list3.add(System.currentTimeMillis());
        list3.add(System.currentTimeMillis());

        //从集合右边插入值
        redisTemplate.opsForList().rightPush("leftList3",list3);

        //从集合右边插入值
        redisTemplate.opsForList().rightPush("rightList",list2);
        //从集合右边删除值
        System.out.println(redisTemplate.opsForList().rightPop("rightList"));
        return leftlist;
       // System.out.println(redisTemplate.opsForList().leftPop("leftList"));
       // System.out.println(redisTemplate.opsForList().rightPop("rightList"));
    }
    /**
     * 设置保存set类型到redis
     */
    @RequestMapping(value = "setAndGetSet")
    public void setAndGetSet(){

        SetOperations<Object, Object> set = redisTemplate.opsForSet();
        set.add("numberSet","1");
        set.add("numberSet","3");
        set.add("numberSet","2");

        Set<Object> resultSet =redisTemplate.opsForSet().members("numberSet");
        System.out.println("resultSet:"+resultSet);

    }


    /**
     * 设置保存set类型到redis
     */
    @RequestMapping(value = "setAndGetZSet")
    public void setAndGetZSet(){

        /**
         * zSet是有序集合，不允许重复的成员
         * 每个元素都会关联一个 double 类型的分数。redis 正是通过分数来为集合中的成员进行从小到大的排序。
         * 有序集合的成员是唯一的,但分数(score)却可以重复。
         */
        ZSetOperations<Object,Object> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add("zSet","one",1);
        zSetOperations.add("zSet","three",3);
        zSetOperations.add("zSet","two",2);

        System.out.println(redisTemplate.opsForZSet().range("zSet",1,3));
        System.out.println(redisTemplate.opsForZSet().range("zSet",0,zSetOperations.size("zSet")));
    }




}
