package edu.xufe.entity;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

import static org.junit.Assert.*;

public class StudentsTest {
    @Test
    public void test01() {
        Students students = new Students();
        students.setSid(1605990418);
        students.setBirthday(new Date());
        students.setGender("男");
        System.out.println("students = " + students);

    }

    @Test
    public void test02() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

       /* Students students = (Students) applicationContext.getBean("student");
        System.out.println("students = " + students);*/
        Students zhangsan1 = (Students) applicationContext.getBean("zhangsan1");
        System.out.println("zhangsan1 = " + zhangsan1);
    }
}