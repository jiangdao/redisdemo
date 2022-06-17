package cn.com.sdpt.redisdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class User implements Serializable {
    @TableId(value="uid",type= IdType.AUTO)
    private Integer uid;
    private String username;
    private String password;
    private String email;
    private String regdate;
}
