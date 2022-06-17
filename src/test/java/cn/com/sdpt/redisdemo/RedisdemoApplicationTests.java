package cn.com.sdpt.redisdemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@SpringBootTest
class RedisdemoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void getTime() throws ParseException {
        String sdt="2023-04-24 21:20:29";
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.parse(sdt).getTime());
    }
}
