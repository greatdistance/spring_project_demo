# Spring的事务管理
## 一、事务回顾
1. 什么是事务
    * 事务：逻辑上的一组操作，组成这组操作的各个单元，要么全做，要么全不做
2. 事务的特性
    * 原子性：事务不可分割
    * 一致性：事务执行前后数据完整性保持一致
    * 隔离性：一个事务的执行不应该受到其他事务的干扰
    * 持久性：事务一旦成功，数据就用持久化到数据库
3. 如果不考虑隔离性引发的安全性问题
* 读问题
    - 脏读：一个事物读到了另一个事务未提交的数据
    - 不可重复读：一个事务读到另一个事务已经提交的update的数据，导致一个事务中多次查询结果不一致
    - 虚读，幻读：一个事务读到另一个事务已提交的insert的数据，导致一个事务中多次查询结果不一致
* 写问题 
    - 丢失更改
3. 解决读问题
    * 设置事务隔离级别
        - Read uncommitted：读未提交，任何问题都解决不了。效率高，安全性低。
        - Read committed：读提交，解决脏读，但是不可重复读和虚读有可能发生。（Oracle 默认隔离级别）
        - Repeatable read:重复读，解决脏读和不可重复读，但是虚读可能发生（MySQL 默认隔离级别）
        - Serializable：解决所有读问题，不允许事务并发。
## 二、Spring的事务管理API
1. PlatformTransactionManager:平台事务管理器
    * 平台事务管理器：是一个接口，是spring用于管理事务的真正的对象
        - DataSourceTransactionManager:底层使用了JDBC来管理事务时使用
        - HibernateTransactionManager:底层使用了Hibernate管理事务时使用
2. TransactionDefinition:事务定义信息
    * 事务定义：用于定义事务的相关信息如隔离级别、超时信息、传播行为、是否只读
3. TransactionStatus：事务的状态
    * 事务状态：用于记录事务在事务管理过程中，事务的状态对象
4. 事务管理的API的关系
    * Spring进行事务管理的时候，首先事务<font clolor=red>平台管理器</font>根据<font clolor=red>事务定义信息</font>进行事务的管理，产生各种状态，将这些状态的信息记录到<font clolor=red>事务状态</font>的对象中
## 三、Spring的事务传播行为
1. Spring中提供了七种事务的传播行为
> 如果遇到了特别复杂的业务逻辑，有可能出现业务层之间的相互调用，事务的传播行为主要用来解决业务层方法相互调用问题
 * 保证多个操作在同一个事务中
    - PROPAGATION_REQUIRED : 默认值，B调用了A，如果A中有事务，使用A中的事务，如果A中没有事务，创建一个新的事务，将操作包含进来
    - PROPAGATION_ SUPPORTS ：支持事务，如果A中有事务，使用A中的事务，如果A中没有事务就不使用事务
    - PROPAGATION_ MANDATORY：如果A中有事务，使用A中的事务，如果A中没有事务则抛出异常
 * 保证多个操作不在同一个事务中
    - PROPAGATION_REQUIRES_NEW: 如果A中有事务，将A的事务挂起，创建新事物，只包含自身操作。如果A中没有事务，创建一个新事务，只包含自身操作。
    - PROPAGATION_ NOT_ SUPPORTED:如果A中有事务，将A的事务挂起。不使用事务管理
    - PROPAGATION_ NEVER:如果A中有事务，抛出异常
 * 嵌套式事务
 - PROPAGATION_NESTED:如果A中有事务，按照A的事务执行，执行完成之后，设置一个保存点，执行B中的操作，如果没有异常执行通过，如果有异常可以选择回滚到最初时，也可以回滚到保存点。
## 四、事务管理
1. 搭建Spring的事务管理环境,演示转账
    * 创建数据库与表，初始化一些数据
    * 创建实体对象
    * 创建Service的接口和实现类
    * 创建Dao的接口和实现类
    * 配置Service和Dao，交给Spring，及其他

```mysql
CREATE TABLE `account` (
      `id` int(11) NOT NULL AUTO_INCREMENT,
      `name` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
      `money` double DEFAULT NULL,
      PRIMARY KEY (`id`)
    ) 
```

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

```java
public interface AccountService {
    /**
     * 转账
     * @param form 转出账号
     * @param to 转入账号
     * @param money 金额
     */
    void transfer(String form,String to,Double money);
}
```

```java
public class AccountServiceImpl implements AccountService {
    @Resource(name = "accountDao")
    private AccountDao accountDao;

    @Override
    public void transfer(String form, String to, Double money) {
        accountDao.outMoney(form,money);
        // 人为异常，测试异常转账时打开
       // int 5/0;
        accountDao.inMoney(to,money);
    }
}
```

```java
public interface AccountDao {
    /**
     * 转出
     * @param from
     * @param money
     */
    void outMoney(String from ,Double money);

    /**
     * 转入
     * @param to
     * @param money
     */
    void inMoney(String to ,Double money);
}
```

```xml
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>edu.xufe</groupId>
  <artifactId>springdemo08</artifactId>
  <version>1.0-SNAPSHOT</version>
  <packaging>war</packaging>

  <name>springdemo08 Maven Webapp</name>
  <!-- FIXME change it to the project's website -->
  <url>http://www.example.com</url>

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
    </dependency>
    <!--  druid相关pom-->
    <!-- https://mvnrepository.com/artifact/com.alibaba/druid -->
    <dependency>
      <groupId>com.alibaba</groupId>
      <artifactId>druid</artifactId>
      <version>1.1.10</version>
    </dependency>

  </dependencies>
  <build>
    <finalName>springdemo08</finalName>
    <pluginManagement><!-- lock down plugins versions to avoid using Maven defaults (may be moved to parent pom) -->
      <plugins>
        <plugin>
          <artifactId>maven-clean-plugin</artifactId>
          <version>3.1.0</version>
        </plugin>
        <!-- see http://maven.apache.org/ref/current/maven-core/default-bindings.html#Plugin_bindings_for_war_packaging -->
        <plugin>
          <artifactId>maven-resources-plugin</artifactId>
          <version>3.0.2</version>
        </plugin>
        <plugin>
          <artifactId>maven-compiler-plugin</artifactId>
          <version>3.8.0</version>
        </plugin>
        <plugin>
          <artifactId>maven-surefire-plugin</artifactId>
          <version>2.22.1</version>
        </plugin>
        <plugin>
          <artifactId>maven-war-plugin</artifactId>
          <version>3.2.2</version>
        </plugin>
        <plugin>
          <artifactId>maven-install-plugin</artifactId>
          <version>2.5.2</version>
        </plugin>
        <plugin>
          <artifactId>maven-deploy-plugin</artifactId>
          <version>2.8.2</version>
        </plugin>
      </plugins>
    </pluginManagement>
  </build>
</project>

```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
   <!-- 配置service-->
    <bean id="accountService" class="edu.xufe.tx.service.impl.AccountServiceImpl" >
        <property name="accountDao" ref="accountDao"/>
    </bean>
    <!--配置Dao-->
    <bean id="accountDao" class="edu.xufe.tx.dao.impl.AccountDaoImpl">
        <property name="dataSource" ref="dataSource"/>
    </bean>
    <!--第二种方式 通过context标签引入-->
    <context:property-placeholder location="classpath:jdbc.properties"/>
    <!--配置Druid连接池-->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="driverClassName" value="${jdbc.driverClassName}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
    </bean>
</beans>
```
2. 编辑测试类：模拟转账及转账过程中出现了异常，查看数据库中的变化
```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class AccountServiceTest {
    @Resource(name = "accountService")
    private AccountService accountService;

    @Test
    public void testTransfer() {
        accountService.transfer("如花","如花1",500d);
    }
}
```
## 五、Spring的事务管理：编程式事务（需要手动写代码）
1. 配置平台事务管理器
```xml
 <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>
```
2. Spring提供了事务管理的模板类
    * 配置事务管理的模板类
    ```xml
    <bean id="transactionTemplate" class="org.springframework.transaction.support.TransactionTemplate">
        <property name="transactionManager" ref="transactionManager"/>
    </bean>
    ```
3. 在业务层注入事务管理的模板
```java
public class AccountServiceImpl implements AccountService {
    /**
     * 注入Dao
     */
    private AccountDao accountDao;

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    /**
     * 注入事务管理的模板
     */
    private TransactionTemplate transactionTemplate;

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public void transfer(String form, String to, Double money) {
        accountDao.outMoney(form,money);
        // 人为异常，测试异常转账时打开
       // int 5/0;
        accountDao.inMoney(to,money);
    }
}
```
```xml
<!-- 配置service-->
    <bean id="accountService" class="edu.xufe.tx.service.impl.AccountServiceImpl">
        <property name="accountDao" ref="accountDao"/>
        <property name="transactionTemplate" ref="transactionTemplate"/>
    </bean>
```
4. 编写事务管理代码
```java
public class AccountServiceImpl implements AccountService {
    /**
     * 注入Dao
     */
    private AccountDao accountDao;

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    /**
     * 注入事务管理的模板
     */
    private TransactionTemplate transactionTemplate;

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public void transfer(final String form, final String to, final Double money) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                accountDao.outMoney(form,money);
                // 人为异常，测试异常转账时打开
                // int 5/0;
                accountDao.inMoney(to,money);
            }
        });


    }
}

```
5. 测试。模拟转账正常和转账发生异常情况