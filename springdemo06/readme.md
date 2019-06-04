#Spring AOP 入门 注解
##一、注解方式开发AOP
- 创建Maven项目引入相应的pom
- 引入Spring的配置文档 applicationContext.xml
- 编写目标类以文章的增删改查为例，并完成配置
`ArticleDao.java`
```java
public class ArticleDao {
    public void save() {
        System.out.println("增加文章信息业务逻辑代码");
    }

    public boolean delete() {
        System.out.println("删除文章信息业务逻辑代码");
        return true;
    }

    public void update() {
        System.out.println("更新文章信息业务逻辑代码");

    }

    public void find() {
        System.out.println("查询文章信息业务逻辑代码");
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
   <!--配置目标类-->
   <bean id="articleDao" class="edu.xufe.dao.ArticleDao"></bean>
```
- 编写切面类并配置
```java
public class MyAspectAnnotation {
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
```xml
 <!--配置切面类-->
    <bean class="edu.xufe.aspect.MyAspectAnnotation" id="myAspectAnnotation"></bean>
```
- 在配置文档中开启注解的AOP开发
```xml
  <!--开启注解的AOP开发-->
  <aop:aspectj-autoproxy/>
```
- 在切面类上使用注解
  - @Aspect 将类识别为切面
  - @Before 前置通知:@Before(value="execution(表达式)")
  > @Before(value = "execution(* edu.xufe.dao.ArticleDao.save())")
  - @AfterReturning：后置通知@AfterReturning(value="execution(表达式)")
  > @AfterReturning(value = "execution(* edu.xufe.dao.ArticleDao.delete(..))",returning = "result")
  - @Around 环绕通知：@Around(value="execution(表达式)")
  > @Around(value = "execution(* edu.xufe.dao.ArticleDao.update(..))")
  - @AfterThrowing(value="execution(表达式)")
  > @AfterThrowing(value = "execution(* edu.xufe.dao.ArticleDao.find(..))",throwing = "e")
  - @After(value="execution(表达式)")
  > @After(value = "execution(* edu.xufe.dao.ArticleDao.find(..))")
  
加上注解后的切面类
```java
@Aspect
public class MyAspectAnnotation {
    /**
     * 前置通知
     *
     * @param joinPoint
     */
    @Before(value = "execution(* edu.xufe.dao.ArticleDao.save(..))")
    public void permissionToCheck(JoinPoint joinPoint) {
        System.out.println("权限校验业务逻辑代码........." + joinPoint);
    }

    /**
     * 后置通知
     * returning = "result" 用于获取返回值
     */
    @AfterReturning(value = "execution(* edu.xufe.dao.ArticleDao.delete(..))",returning = "result")
    public void writeLog(Object result) {
        System.out.println("日志业务逻辑代码........."+result);
    }

    /**
     * 环绕通知
     *
     * @param proceedingJoinPoint
     * @return
     */
    @Around(value = "execution(* edu.xufe.dao.ArticleDao.update(..))")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("环绕通知方法执行前业务逻辑代码。。。。。。。。。");

        Object object = proceedingJoinPoint.proceed();

        System.out.println("环绕通知方法执行后业务逻辑代码。。。。。。。。。");
        return object;
    }

    /**
     * 异常通知
     */
    @AfterThrowing(value = "execution(* edu.xufe.dao.ArticleDao.find(..))",throwing = "e")
    public void afterThrowing(Throwable e) {
        System.out.println("异常通知逻辑代码........." + e);
    }

    @After(value = "execution(* edu.xufe.dao.ArticleDao.find(..))")
    public void after() {
        System.out.println("最终通知逻辑代码.........");
    }
}
```
- 编写测试
```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")

public class ArticleDaoTest {
    @Resource(name = "articleDao")
    private ArticleDao articleDao;

    @Test
    public void test() {
        articleDao.save();
        articleDao.update();
        articleDao.delete();
        articleDao.find();
    }
}
```
- 切入点的配置 注解
  - @Pointcut(value="execution(表达式)")
  > @Pointcut(value = "execution(* edu.xufe.dao.ArticleDao.save(..))")
        private void pointcut() {
        }
        
- 切入点的使用
> @Before(value = "类名.pointcut()")

> 使用切入点配置：便于维护，当方法很多时我们修改@Before(value="execution(表达式)") 这类配置显得很多很麻烦
  但是修改切入点只改一个地方 及@Pointcut(value="execution(表达式)")中的表达式。