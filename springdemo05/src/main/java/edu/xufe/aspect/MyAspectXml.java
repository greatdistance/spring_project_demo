package edu.xufe.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @ClassName MyAspectXml
 * @Description 切面类 通过XML配置
 * @Author greatDistance
 * @Date 2019/5/28 18:06
 * @Version 1.0
 */
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
