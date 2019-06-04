package edu.xufe.entity;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

import static org.junit.Assert.*;

public class StudentsTest {

    @Test
    public void getStudent() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        Students student = (Students) applicationContext.getBean("student");
        System.out.println("student = " + student);

      /*  Students student1 = applicationContext.getBean(Students.class);
        System.out.println("student1 = " + student1);
*/
        /*Students zhangsan = applicationContext.getBean("zhangsan", Students.class);
        System.out.println("zhangsan = " + zhangsan);*/

        Students zhangsan1 = applicationContext.getBean("zhangsan1", Students.class);
        System.out.println("zhangsan1 = " + zhangsan1);
        Students zhangliu = applicationContext.getBean("zhangliu", Students.class);
        System.out.println("zhangliu = " + zhangliu);

       /* Students studentFactory = (Students) applicationContext.getBean("studentFactory", new Object[]{1010, "王五", "男", new Date()});
        System.out.println("studentFactory = " + studentFactory);*/
    }
}