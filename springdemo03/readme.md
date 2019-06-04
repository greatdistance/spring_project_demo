#spring Bean的获取与注入
##一、spring Bean的注入
准备：
1. Bean `Students`类
```aidl
public class Students {
    private int sid;
   
    private String username;
    
    private String gender;
   
    private Date birthday;

    public Students(int sid, String username, String gender, Date birthday) {
        this.sid = sid;
        this.username = username;
        this.gender = gender;
        this.birthday = birthday;
    }
    public Students() {
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "Students{" +
                "sid=" + sid +
                ", username='" + username + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
```
2. convert包下自定义字符串转日期`MyDateConvert`类
```aidl
public class MyDateConvert extends PropertyEditorSupport {
    /**
     * 字符串格式
     * yyyy-MM-dd yyyy/MM/dd
     * 或是其他 应由外部传入
     */
    private String format;

    public MyDateConvert(String format) {
        this.format = format;
    }
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        SimpleDateFormat sdf = new SimpleDateFormat(this.format);
        try {
            this.setValue(sdf.parse(text));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
```
3. convert包下自定义了一个注册器`MyCustomDateEditorRegistrar`类
```aidl
public class MyCustomDateEditorRegistrar implements PropertyEditorRegistrar {

    private String format;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public void registerCustomEditors(PropertyEditorRegistry registry) {
        registry.registerCustomEditor(java.util.Date.class, new MyDateConvert(format));
    }

}
```
4. applicationContext.xml中配置String转Date的bean
```aidl
  <!-- 使 Spring转换为java.util.Date-->
    <bean class="org.springframework.beans.factory.config.CustomEditorConfigurer">
        <property name="propertyEditorRegistrars">
            <list>
                <bean class="edu.xufe.convert.MyCustomDateEditorRegistrar">
                    <property name="format" value="yyyy-MM-dd"></property>
                </bean>
            </list>
        </property>
    </bean>
```
5. 开启包扫描 扫描根包 edu
```aidl
    <context:annotation-config/>
    <context:component-scan base-package="edu"/>
```

1. 属性注入
```aidl
    <bean id="student" class="edu.xufe.entity.Students">
        <property name="sid" value="1605990418"></property>
        <property name="username" value="李四"></property>
        <property name="gender" value="男"></property>
        <property name="birthday">
            <bean factory-bean="dateFormat" factory-method="parse">
                <constructor-arg value="1996-11-28"/>
            </bean>
        </property>
    </bean>
```
2. 构造方法注入
```aidl
    <bean id="zhangsan1" class="edu.xufe.entity.Students">
        <constructor-arg name="sid" value="1605990418"></constructor-arg>
        <constructor-arg name="username" value="张三1"></constructor-arg>
        <constructor-arg name="gender" value="女"></constructor-arg>
        <constructor-arg name="birthday" value="1996-11-28"></constructor-arg>
    </bean>
```
3. 注解注入
> 1.bean加注解@Compent<br />
> 2.属性加注解@Value("值")
```aidl
@Component("zhangliu")
public class Students {
    @Value("1605990418")
    private int sid;
    @Value("赵六")
    private String username;
    @Value("女")
    private String gender;
    @Value("1996-11-20")
    private Date birthday;

    public Students(int sid, String username, String gender, Date birthday) {
        this.sid = sid;
        this.username = username;
        this.gender = gender;
        this.birthday = birthday;
    }
    public Students() {
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "Students{" +
                "sid=" + sid +
                ", username='" + username + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
```
##一、spring getBean的方式
1. getBean("id | name"); 
> 根据id或者name去查找bean 缺点需要强制类型转换
2. getBean(Class);
> 根据类型去查找bean 缺点bean是必须唯一
3. getBean("id | name",getBean("id | name"));
> 在指定类型的同时按照id或name去查找bean
4. getBean("id | name",Object[]);
> 工厂方法 Object[]传入参数 