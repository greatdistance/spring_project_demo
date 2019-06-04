package edu.xufe.config;

import edu.xufe.dao.Usb;
import edu.xufe.dao.impl.UsbDisk;
import edu.xufe.entity.Students;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * @ClassName MyApplicationContext
 * @Description 注解定义bean
 * @Author greatDistance
 * @Date 2019/5/23 23:55
 * @Version 1.0
 */
@Configuration
public class MyApplicationContext {

    /**
     * 相当于在xml中定义了一个叫device1的bean
     *
     * @return
     */
    @Bean(name = "device1")
    public Usb getUsbDevice() {
        Usb device = new UsbDisk();
        return device;
    }

    @Bean(name = "student1")
    public Students getStudent() {
        Students students = new Students();
        students.setBirthday(new Date());
        students.setSid(1605990418);
        return students;
    }

}
