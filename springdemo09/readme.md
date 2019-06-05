# Spring的事务管理--声明式事务
## 一、XML方式的声明式事务
1. 搭建Spring的事务管理环境,演示转账
    * 创建maven项目引入pom
    * 创建数据库与表，初始化一些数据
    * 创建实体对象
    * 创建Service的接口和实现类
    * 创建Dao的接口和实现类
    * 配置Service和Dao，交给Spring，及其他
* 创建数据库与表

```mysql
CREATE TABLE `account` (
      `id` int(11) NOT NULL AUTO_INCREMENT,
      `name` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
      `money` double DEFAULT NULL,
      PRIMARY KEY (`id`)
    ) 
```
* pom
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
    </dependency>
    <!--  druid相关pom-->
    <!-- https://mvnrepository.com/artifact/com.alibaba/druid -->
    <dependency>
      <groupId>com.alibaba</groupId>
      <artifactId>druid</artifactId>
      <version>1.1.10</version>
    </dependency>


      <!-- https://mvnrepository.com/artifact/org.springframework/spring-aop -->
      <dependency>
          <groupId>org.springframework</groupId>
          <artifactId>spring-aop</artifactId>
          <version>${spring.version}</version>
      </dependency>

    <!-- https://mvnrepository.com/artifact/org.springframework/spring-aspects -->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-aspects</artifactId>
      <version>${spring.version}</version>
    </dependency>

    <!-- https://mvnrepository.com/artifact/org.aspectj/aspectjweaver -->
    <dependency>
      <groupId>org.aspectj</groupId>
      <artifactId>aspectjweaver</artifactId>
      <version>1.9.2</version>
    </dependency>


  </dependencies>
```
* service
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
* serviceImpl
```java
public class AccountServiceImpl implements AccountService {
    /**
     * 注入Dao
     */
    private AccountDao accountDao;

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }


    @Override
    public void transfer(String form, String to, Double money) {
        accountDao.outMoney(form, money);
        // 人为异常，测试异常转账时打开
        int a = 5 / 0;
        accountDao.inMoney(to, money);
    }
}

```
* dao
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
* daoImpl
```java
public class AccountDaoImpl extends JdbcDaoSupport implements AccountDao {
    @Override
    public void outMoney(String from, Double money) {
        String sql = "update account set money = money - ? where name=?";
        this.getJdbcTemplate().update(sql,money,from);
    }

    @Override
    public void inMoney(String to, Double money) {
        String sql = "update account set money = money + ? where name=?";
        this.getJdbcTemplate().update(sql,money,to);
    }
}
```
* 配置事务管理器
```xml
 <!--配置平台事务管理器-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>
```
* 配置增强
```xml
<!-- 配置事务的增强-->
    <tx:advice id="txAdvice" transaction-manager="transactionManager">
        <tx:attributes>
            <tx:method name="transfer" propagation="REQUIRED"/>
        </tx:attributes>
    </tx:advice>
```
* 配置AOP
```xml
 <!--配置AOP-->
    <aop:config>
        <aop:pointcut id="pointcut1" expression="execution(* edu.xufe.tx.service.impl.AccountServiceImpl.*(..))"/>
        <aop:advisor advice-ref="txAdvice" pointcut-ref="pointcut1"/>
    </aop:config>
```
* jdbc.properties
```properties
jdbc.driverClassName=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql:///springdemo07
jdbc.username=root
jdbc.password=mysql
```
* 完整的applicationContext.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context" xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd">
    <!-- 配置service-->
    <bean id="accountService" class="edu.xufe.tx.service.impl.AccountServiceImpl">
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
    <!--配置平台事务管理器-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>
    <!-- 配置事务的增强-->
    <tx:advice id="txAdvice" transaction-manager="transactionManager">
        <tx:attributes>
            <tx:method name="transfer" propagation="REQUIRED"/>
        </tx:attributes>
    </tx:advice>
    <!--配置AOP
    aop:advisor 单个切入点
    aop:aspect  多个切入点-->
    <aop:config>
        <aop:pointcut id="pointcut1" expression="execution(* edu.xufe.tx.service.impl.AccountServiceImpl.*(..))"/>
        <aop:advisor advice-ref="txAdvice" pointcut-ref="pointcut1"/>
    </aop:config>
    <!-- 配置事务管理的模板类-->
    <bean id="transactionTemplate" class="org.springframework.transaction.support.TransactionTemplate">
        <property name="transactionManager" ref="transactionManager"/>
    </bean>
</beans>
```
2. 编写测试，演示正常转账和转账过程中出现异常的情况
```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class AccountServiceTest {
    @Resource(name = "accountService")
    private AccountService accountService;

    @Test
    public void testTransfer() {
        accountService.transfer("如花", "如花1", 500d);
    }
}
```
## 二、注解方式的声明式事务
1. 恢复环境取消在applicationContext.xml中的AOP配置与争抢
取消后的applicationContext.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context" xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd">
    <!-- 配置service-->
    <bean id="accountService" class="edu.xufe.tx.service.impl.AccountServiceImpl">
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
    <!--配置平台事务管理器-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>
    <!-- 配置事务的增强-->
    <!--<tx:advice id="txAdvice" transaction-manager="transactionManager">
        <tx:attributes>
            <tx:method name="transfer" propagation="REQUIRED"/>
        </tx:attributes>
    </tx:advice>-->
    <!--配置AOP
    aop:advisor 单个切入点
    aop:aspect  多个切入点-->
    <!--<aop:config>
        <aop:pointcut id="pointcut1" expression="execution(* edu.xufe.tx.service.impl.AccountServiceImpl.*(..))"/>
        <aop:advisor advice-ref="txAdvice" pointcut-ref="pointcut1"/>
    </aop:config>-->

    <!--开启事务注解-->
    <tx:annotation-driven transaction-manager="transactionManager"/>
    <!-- 配置事务管理的模板类-->
    <bean id="transactionTemplate" class="org.springframework.transaction.support.TransactionTemplate">
        <property name="transactionManager" ref="transactionManager"/>
    </bean>
</beans>
```
2. 开启注解事务
```xml
<!--开启事务注解-->
    <tx:annotation-driven transaction-manager="transactionManager"/>
```
3. 在业务层添加事务管理的注解
@Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
4.编写测试，演示正常转账和转账过程中出现异常的情况
源代码github：<https://github.com/greatdistance/spring_project_demo/tree/master/springdemo09>