package edu.xufe.entity.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ClassName MovieStarProxy
 * @Description 电影明星代理类 经纪人
 * @Author greatDistance
 * @Date 2019/5/25 17:06
 * @Version 1.0
 */
public class MovieStarJdkProxy implements InvocationHandler {
    /**
     * 被代理的原始对象
     */
    private Object originalObject;

    /**
     * 绑定代理对象
     *
     * @param object
     * @return
     */
    public Object bind(Object object) {
        this.originalObject = object;
        return Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getSuperclass().getInterfaces(), this);
    }


    /**
     * 演戏之前要完成的事情
     */
    private void beforeAct() {
        System.out.println("谈档期");
        System.out.println("谈广告合作");
        System.out.println("谈片酬");
        System.out.println("签合同");
    }

    /**
     * 演戏之后要完成的事情
     */
    public void afterAct() {
        System.out.println("出席首映仪式");
        System.out.println("出席颁奖典礼");
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        this.beforeAct();
        Object result = method.invoke(this.originalObject, args);
        this.afterAct();
        return result;
    }
}
