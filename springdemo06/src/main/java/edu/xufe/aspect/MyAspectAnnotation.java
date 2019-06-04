package edu.xufe.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * @ClassName MyAspectAnnocation
 * @Description 注解方式的切面类
 * @Author greatDistance
 * @Date 2019/5/29 0:39
 * @Version 1.0
 */
@Aspect
public class MyAspectAnnotation {
    /**
     * 前置通知
     *
     * @param joinPoint
     */
    @Before(value = "MyAspectAnnotation.pointcut1()")
    public void permissionToCheck(JoinPoint joinPoint) {
        System.out.println("权限校验业务逻辑代码........." + joinPoint);
    }

    /**
     * 后置通知
     * returning = "result" 用于获取返回值
     */
    @AfterReturning(value = "MyAspectAnnotation.pointcut2()", returning = "result")
    public void writeLog(Object result) {
        System.out.println("日志业务逻辑代码........." + result);
    }

    /**
     * 环绕通知
     *
     * @param proceedingJoinPoint
     * @return
     */
    @Around(value = "MyAspectAnnotation.pointcut3()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("环绕通知方法执行前业务逻辑代码。。。。。。。。。");

        Object object = proceedingJoinPoint.proceed();

        System.out.println("环绕通知方法执行后业务逻辑代码。。。。。。。。。");
        return object;
    }

    /**
     * 异常通知
     */
    @AfterThrowing(value = "MyAspectAnnotation.pointcut4()", throwing = "e")
    public void afterThrowing(Throwable e) {
        System.out.println("异常通知逻辑代码........." + e);
    }

    @After(value = "MyAspectAnnotation.pointcut4()")
    public void after() {
        System.out.println("最终通知逻辑代码.........");
    }


    /**
     * 切入点注解
     */
    @Pointcut(value = "execution(* edu.xufe.dao.ArticleDao.save(..))")
    private void pointcut1() {
    }

    @Pointcut(value = "execution(* edu.xufe.dao.ArticleDao.delete(..))")
    private void pointcut2() {
    }

    @Pointcut(value = "execution(* edu.xufe.dao.ArticleDao.update(..))")
    private void pointcut3() {
    }

    @Pointcut(value = "execution(* edu.xufe.dao.ArticleDao.find(..))")
    private void pointcut4() {
    }
}
