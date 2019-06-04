#我的第二个spring工程
##Spring入门
一、回顾第一个spring
>见edu.entity.Students 及配置文件 applicationContext.xml 及其对应的测试类

二、开始今天的学习（初步认识IOC）
1. dao包下编写接口Usb
2. dao.impl包下编写Usb接口的实现类 UsbDisk(U盘) MoveDisk(移动硬盘)
3. service包下编写UsbService 因为结构简单service就不按照写接口与实现类的方法来规范代码结构了
4. 编写UsbService的测试方法read1()
5. 测试体验了传统新建对象注入不方便的地方
6. 使用IOC来注入对象 编写applicationContext.xml 配置device bean
7. 再次编写测试类方法 read2()
8. 上面的配置还是需要自己注入usb接口
9. applicationContext.xml中配置usbService bean
8. 再次编写测试类方法 read3()

三、使用注解来配置bean
1. config包下编写类
2. 返回UsbService类 给类加注解`@Service`在device属性上加注解`@Resource` 测试类也有相应的注解
3. 在applicationContext.xml中配置 开启注解扫描
4. 编写测试方法read4()

四、解决Students中Data类型不能注入
1. 若使用全注解的方法就不会出现这个问题 我们可以直接`new Date()` 但是使用xml配置spring就不知道怎么处理了
2. 全注解演示 在config包中编写一个 student1 bean
3. StudentTest测试是否能通过
4. dao包中新建StudentDao
5. dao.impl包中新建StudentDaoImpl
6. service包中新建StudentService
7. StudentServiceTest编写测试方法save()

五、applicationContext.xml配置使`String` 转`Date`<br/>
方法一:
1. 在bean中配置一个bean dateFormat
```aidl
    <bean id="dateFormat" class="java.text.SimpleDateFormat">
        <constructor-arg value="yyyy-MM-dd"></constructor-arg>
    </bean>
```
2. 对于需要转换的属性配置如下
```aidl
        <property name="birthday">
            <bean factory-bean="dateFormat" factory-method="parse">
                <constructor-arg value="1996-11-28"/>
            </bean>
        </property>
```
方法二：
1. 在convert包下新建类MyDateConvert（自定义字符串转日期的规则）和MyCustomDateEditorRegistrar（自定了一个注册器类）
