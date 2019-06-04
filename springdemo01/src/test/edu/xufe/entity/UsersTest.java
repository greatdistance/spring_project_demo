package edu.xufe.entity;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

public class UsersTest {
    /**
     * 测试自己创建对象实例
     */
    @Test
    public void test() {
        Users myUser = new Users(10, "张三", "123456");
        System.out.println(myUser);
    }

    /**
     * 测试由容器注入bean
     */
    @Test
    public void test1() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        Users myUser = (Users) applicationContext.getBean("myUser");
        System.out.println(myUser);
    }
}