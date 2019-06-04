#Spring AOP 入门 AspectJ + XML
##一、AOP开发相关术语
1. JoinPoint:连接点。可以被拦截到的点(增删改查的方法都可以被增强，这些方法就可以成为是连接点)
2. Pointcut:切入点。真正被拦截到的点。
3. Advice:通知、增强。对方法做权限校验、日志功能的方法称为是通知。
4. Introduction:引介。类层面的增强。
5. Target:被增强的对象。
6. Weaving:织入。将通知（Advice）应用到目标（Target）的过程。
7. Proxy:代理对象。
8. Aspect:切面。多个通知和多个切入点的组合。
##二、AspectJ + XML方式开发AOP
- 创建Maven项目引入相应的pom
- 引入Spring的配置文档 applicationContext.xml
- 编写目标<font color=red>接口</font>(Target)及实现类(以用户的增删改查为例，只模拟，无实际意义)并完成配置
`UsersDao.java`
```java
public interface UsersDao {
    /**
     * 增
     */
    void save();

    /**
     * 删
     */
    void delete();

    /**
     * 改
     */
    void update();

    /**
     * 查
     */
    void find();
}
```
`UsersDaoImpl.java`
```java
public class UsersDaoImpl implements UsersDao {
    @Override
    public void save() {
        System.out.println("增加用户信息业务逻辑代码");
    }

    @Override
    public boolean delete() {
        System.out.println("删除用户信息业务逻辑代码");
        return true;
    }

    @Override
    public void update() {
        System.out.println("更新用户信息业务逻辑代码");

    }

    @Override
    public void find() {
        System.out.println("查询用户信息业务逻辑代码");
        // 演示异常通知时打开
       // int i = 10 / 0;
    }
}
```
> save演示前置通知

> delete演示后置通知

> update演示环绕通知

> find演示异常通知和最终通知

将bean交给spring管理
```xml
    <!--配置目标对象：被增强的对象-->
    <bean id="userDao" class="edu.xufe.dao.impl.UsersDaoImpl"></bean>
```
- 编写测试类`UsersDaoTest`
```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UsersDaoTest {
    @Resource(name = "usersDao")
    private UsersDao usersDao;

    @Test
    public void test() {
        usersDao.save();
        usersDao.update();
        usersDao.update();
        usersDao.update();
    }
}
```
- 编写切面类  `MyAspectXml`
```java
public class MyAspectXml {
    /**
     * 前置通知
     *
     * @param joinPoint
     */
    public void permissionToCheck(JoinPoint joinPoint) {
        System.out.println("权限校验业务逻辑代码........." + joinPoint);
    }

    /**
     * 后置通知
     */
    public void writeLog(Object result) {
        System.out.println("日志业务逻辑代码........." + result);
    }

    /**
     * 环绕通知
     *
     * @param proceedingJoinPoint
     * @return
     */
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("环绕通知方法执行前业务逻辑代码。。。。。。。。。");

        Object object = proceedingJoinPoint.proceed();

        System.out.println("环绕通知方法执行后业务逻辑代码。。。。。。。。。");
        return object;
    }

    /**
     * 异常通知
     */
    public void afterThrowing(Throwable e) {
        System.out.println("异常通知逻辑代码........." + e);
    }

    public void after() {
        System.out.println("最终通知逻辑代码.........");
    }
}
```
- 将切面类交给Spring管理
```xml
     <!--将切面类交给Spring管理-->
    <bean id="myAspect" class="edu.xufe.aspect.MyAspectXml"></bean>
```
- AOP配置
```xml
    <aop:config>
        <!--表达式配置哪些类的哪些方法需要增强-->
        <aop:pointcut id="pointcut1" expression="execution(* edu.xufe.dao.impl.UsersDaoImpl.save(..))"/>
        <!--配置切面-->
        <aop:aspect ref="myAspect">
            <aop:before method="permissionToCheck" pointcut-ref="pointcut1"/>
        </aop:aspect>
    </aop:config>
```
- 再次测试
##三、Spring中通知类型
- 前置通知：在目标方法执行之前进行操作
  - 前置通知可获得切入点信息
```xml
 <!--配置切面-->
        <aop:aspect ref="myAspect">
            <aop:before method="permissionToCheck" pointcut-ref="pointcut1"/>
        </aop:aspect>
```
- 后置通知：在目标方法执行之后进行操作
  - 后置通知可获得方法的返回值 改delete()方法有返回值

```xml
  <aop:pointcut id="pointcut2" expression="execution(* edu.xufe.dao.impl.UsersDaoImpl.delete(..))"/>
```
  ```xml
  <!--配置切面-->
        <aop:aspect ref="myAspect">
            <!--前置通知-->
            <aop:before method="permissionToCheck" pointcut-ref="pointcut1"/>
            <!--后置通知-->
            <aop:after-returning method="writeLog" pointcut-ref="pointcut2" returning="result"/>
        </aop:aspect>
```
- 环绕通知：在目标方法执行之前和之后进行操作
  - 环绕通知:
```xml
 <aop:pointcut id="pointcut3" expression="execution(* edu.xufe.dao.impl.UsersDaoImpl.update(..))"/>    
```
```xml
 <!--配置切面-->
        <aop:aspect ref="myAspect">
            <!--前置通知-->
            <aop:before method="permissionToCheck" pointcut-ref="pointcut1"/>
            <!--后置通知
                result 用于获取返回值
                需要与writeLog方法参数同名
            -->
            <aop:after-returning method="writeLog" pointcut-ref="pointcut2" returning="result"/>
            <!--环绕通知-->
            <aop:around method="around" pointcut-ref="pointcut3"/>
        </aop:aspect>
```
  
- 异常抛出通知：在程序出现异常的时候，进行操作
```xml
<aop:pointcut id="pointcut4" expression="execution(* edu.xufe.dao.impl.UsersDaoImpl.find(..))"/>
```
```xml
 <!--配置切面-->
        <aop:aspect ref="myAspect">
            <!--前置通知-->
            <aop:before method="permissionToCheck" pointcut-ref="pointcut1"/>
            <!--后置通知
                result 用于获取返回值
                需要与writeLog方法参数同名
            -->
            <aop:after-returning method="writeLog" pointcut-ref="pointcut2" returning="result"/>
            <!--环绕通知-->
            <aop:around method="around" pointcut-ref="pointcut3"/>
            <!--异常通知-->
            <aop:after-throwing method="afterThrowing" pointcut-ref="pointcut4" throwing="e"/>
        </aop:aspect>
```
- 最终通知：无论代码是否有异常，总是会执行
```xml
 <!--配置切面-->
        <aop:aspect ref="myAspect">
            <!--前置通知-->
            <aop:before method="permissionToCheck" pointcut-ref="pointcut1"/>
            <!--后置通知
                result 用于获取返回值
                需要与writeLog方法参数同名
            -->
            <aop:after-returning method="writeLog" pointcut-ref="pointcut2" returning="result"/>
            <!--环绕通知-->
            <aop:around method="around" pointcut-ref="pointcut3"/>
            <!--异常通知-->
            <aop:after-throwing method="afterThrowing" pointcut-ref="pointcut4" throwing="e"/>
            <!--最终通知-->
            <aop:after method="after" pointcut-ref="pointcut4"/>
        </aop:aspect>
```
- 引介通知：
##三、Spring切入点表达式写法
- 基于execution的函数完成的
- 语法
   - [访问修饰符]方法返回值 包名.类名.方法名(参数)
   - `public edu.xufe.dao.impl.UsersDaoImpl.save(..)`
   - `*`代表任一
   - `* *.*.*.*.*.*Dao.save(..)` 表示xxxDao下的save()方法
   - `* edu.xufe.dao.impl.UsersDaoImpl+save(..)` 表示当前类及子类下的save()方法 
##四、基于注解的AOP开发
