package edu.xufe.entity.cglibproxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @ClassName MovieStarCglibAbstractClassProxy
 * @Description TODO
 * @Author greatDistance
 * @Date 2019/5/27 14:55
 * @Version 1.0
 */
public class MovieStarCglibAbstractClassProxy implements MethodInterceptor {
    /**
     * 原始对象
     */
    private Object originalObject;

    /**
     * 返回一个代理对象。
     *
     * @param object
     * @return
     */
    public Object bind(Object object) {
        this.originalObject = object;
        Enhancer enhancer = new Enhancer();
        // enhancer.setInterfaces(object.getClass().getSuperclass().getInterfaces());
        enhancer.setSuperclass(object.getClass().getSuperclass().getSuperclass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    //演戏之前要完成的事情
    private void beforeAct() {
        System.out.println("谈档期");
        System.out.println("谈广告合作");
        System.out.println("谈片酬");
        System.out.println("签合同");
    }

    //演戏之后要完成的事情
    public void afterAct() {
        System.out.println("出席首映仪式");
        System.out.println("出席颁奖典礼");
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        beforeAct();
        //真正调用成龙的演戏方法
        Object result = method.invoke(this.originalObject, objects);
        afterAct();
        return result;
    }
}
