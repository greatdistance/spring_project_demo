package edu.xufe.service;

import edu.xufe.dao.Usb;
import edu.xufe.dao.impl.MoveDisk;
import edu.xufe.dao.impl.UsbDisk;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UsbServiceTest {
    private static ApplicationContext applicationContext = null;

    @Resource
    private UsbService usbService1;

    @BeforeClass
    public static void init() {
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    }

    /**
     * 传统写法 自己注入UsbDisk对象
     * 但是如果我想使用MoveDisk 记得改源代码
     * 改编源代码又得需要重新编译
     */
    @Test
    public void read1() {
        UsbService usbService = new UsbService();
        /*UsbDisk usbDisk = new UsbDisk();
        usbService.setDevice(usbDisk);*/

        MoveDisk moveDisk = new MoveDisk();
        usbService.setDevice(moveDisk);

        usbService.read();
    }

    /**
     * 使用容器注入 如果想改变实现类 无需改变源代码 更改xml中bean对应的class属性即可
     */
    @Test
    public void read2() {
        UsbService usbService = new UsbService();
        Usb usb = (Usb) applicationContext.getBean("device");
        usbService.setDevice(usb);
        usbService.read();
    }

    /**
     * 与read2()相比 我们的接口也由容器来注入
     * 我们不用再写 usbService.setDevice(usb); 由容器来帮我们来完成注入
     */
    @Test
    public void read3() {
        UsbService usbService = (UsbService) applicationContext.getBean("usbService");
        usbService.read();
    }

    @Test
    public void read4() {

        usbService1.read();
    }

    @Test
    public void write() {
    }
}