# Spring的JDBC的模板使用
## 一、原始方法使用Spring的JDBC的模板
新建maven项目导入pom
```xml
<properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <spring.version>4.3.18.RELEASE</spring.version>
        <mysql.version>5.1.8</mysql.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>


        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.springframework/spring-beans -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-beans</artifactId>
            <version>${spring.version}</version>
        </dependency>


        <!-- https://mvnrepository.com/artifact/org.springframework/spring-context -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.springframework/spring-expression -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-expression</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>${spring.version}</version>
            <scope>compile</scope>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.springframework/spring-jdbc -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.springframework/spring-tx -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-tx</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>${mysql.version}</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.springframework/spring-aop -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aop</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/log4j/log4j -->
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.17</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/commons-logging/commons-logging -->
        <dependency>
            <groupId>commons-logging</groupId>
            <artifactId>commons-logging</artifactId>
            <version>1.2</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>compile</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>${spring.version}</version>
            <scope>compile</scope>
        </dependency>

    </dependencies>
```
1. 创建数据库和表
```mysql
CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `money` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) 
```
log4j.properties 文件
```properties
### direct log messages to stdout ###
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.Target=System.err
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%d{ABSOLUTE} %5p %c{1}:%L - %m%n

### direct messages to file mylog.log ###
log4j.appender.file=org.apache.log4j.FileAppender
log4j.appender.file.File=c\:springdemo07.log
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=%d{ABSOLUTE} %5p %c{1}:%L - %m%n

### set log levels - for more verbose logging change 'info' to 'debug' ###
# error warn info debug trace
log4j.rootLogger= info, stdout,file
```
2. 编写测试方法
```java
public class JdbcDemo01 {
    @Test
    public void demo01(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql:///springdemo07");
        dataSource.setUsername("root");
        dataSource.setPassword("mysql");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("insert into account value (null,?,?)","余永涛",10000d);
    }
}
```
## 二、将连接池和模板交给Spring管理
1. 引入spring的配置文件applicationContext.xml
```xml
 <!--Spring的内置连接池-->
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <!--属性注入-->
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql:///springdemo07"/>
        <property name="username" value="root"/>
        <property name="password" value="mysql"/>
    </bean>
    <!--配置Spring的JDBC模板-->
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <property name="dataSource" ref="dataSource"/>
    </bean>
```
2. 使用JDBC模板
```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class JdbcDemo02 {
    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Test
    public void demo02() {
        jdbcTemplate.update("insert into account value (null,?,?)", "张三", 10000d);
    }
}
```
## 三、开源连接池的使用
1. DBCP
 * 引入DBCP相关pom
 ```xml
 <!--  DBCP相关pom-->
        <!-- https://mvnrepository.com/artifact/commons-dbcp/commons-dbcp -->
        <dependency>
            <groupId>commons-dbcp</groupId>
            <artifactId>commons-dbcp</artifactId>
            <version>1.3</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/commons-pool/commons-pool -->
        <dependency>
            <groupId>commons-pool</groupId>
            <artifactId>commons-pool</artifactId>
            <version>1.5.4</version>
        </dependency>
```
* DBCP applicationContext.xml配置
```xml
 <!--配置DBCP连接池-->
    <bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql:///springdemo07"/>
        <property name="username" value="root"/>
        <property name="password" value="mysql"/>
    </bean>
```
* 运行测试
2. C3P0的使用
* 引入C3P0 pom
```xml
<!--  c3p0相关pom-->
        <!-- https://mvnrepository.com/artifact/com.mchange/c3p0 -->
        <dependency>
            <groupId>com.mchange</groupId>
            <artifactId>c3p0</artifactId>
            <version>0.9.5.2</version>
        </dependency>
```
* C3P0 applicationContext.xml配置
```xml
  <!--配置C3P0连接池-->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="driverClass" value="com.mysql.jdbc.Driver"/>
        <property name="jdbcUrl" value="jdbc:mysql:///springdemo07"/>
        <property name="user" value="root"/>
        <property name="password" value="mysql"/>
    </bean>
```
* 运行测试
3. Druid
* 引入Druid pom
```xml
 <!--  druid相关pom-->
        <!-- https://mvnrepository.com/artifact/com.alibaba/druid -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.1.10</version>
        </dependency>
```
* Druid applicationContext.xml配置
```xml
 <!--配置Druid连接池-->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql:///springdemo07"/>
        <property name="username" value="root"/>
        <property name="password" value="mysql"/>
    </bean>
```
* 运行测试
4. 抽取连接池配置到属性文件
* classpath下新建文件jdbc.properties
```properties
jdbc.driverClass=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql:///springdemo07
jdbc.username=root
jdbc.password=mysql
```
* 在spring配置文件中引入属性文件
```xml
 <!--引入属性文件-->
    <!--第一种方式 通过一个bean标签引入 很少使用-->
    <bean class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
        <property name="location" value="classpath:jdbc.properties"/>
    </bean>
```
```xml
 <!--第二种方式 通过context标签引入-->
    <context:property-placeholder location="classpath:jdbc.properties"/>
```
* 属性文件的使用 如在druid连接池配置中使用 `${key}`
```xml
<!--配置Druid连接池-->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="driverClassName" value="${jdbc.driverClassName}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
    </bean>
```
## 四、使用JDBC模板进行CRUD操作
1. 实体对象 Account
```java
public class Account {
    private Integer id;
    private String name;
    private Double money;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", money=" + money +
                '}';
    }
}

```
2. 测试
```java
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @ClassName JdbcDemo03
 * @Description TODO
 * @Author greatDistance
 * @Date 2019/6/4 20:44
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class JdbcDemo03 {
    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    /**
     * 增
     */
    @Test
    public void testSave() {
        jdbcTemplate.update("insert into account value (null,?,?)", "如花1", 10000d);
    }

    /**
     * 修改
     */
    @Test
    public void testUpdate() {
        jdbcTemplate.update("update account set name=?,money=? where id =?", "修改如花", 8000d, 1);
    }

    /**
     * 删除
     */
    @Test
    public void testDelete() {
        jdbcTemplate.update("delete from account where id=?", 1);
    }

    /**
     * 查询单个
     */
    @Test
    public void testQuery() {
        String name = jdbcTemplate.queryForObject("select name from account where id = ?", String.class, 1);
        Long count = jdbcTemplate.queryForObject("select count(*) from account", Long.class);
        System.out.println("name = " + name);
        System.out.println("count = " + count);
    }

    /**
     * 封装一个结果集
     */
    class MyRowMapper implements RowMapper<Account> {
        @Override
        public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
            Account account = new Account();
            account.setId(rs.getInt("id"));
            account.setName(rs.getString("name"));
            account.setMoney(rs.getDouble("money"));
            return account;
        }
    }

    /**
     * 查询所有属性
     * 封装到一个对象中
     */
    @Test
    public void testQueryObject(){
        Account account = jdbcTemplate.queryForObject("select * from account where id = ?", new MyRowMapper(), 1);
        System.out.println(account);
    }

    /**
     * 查询多条记录
     */
    @Test
    public void testQueryObjectList(){
        List<Account> list = jdbcTemplate.query("select * from account", new MyRowMapper());
        for (Account account : list) {
            System.out.println(account);
        }
    }
}

```

