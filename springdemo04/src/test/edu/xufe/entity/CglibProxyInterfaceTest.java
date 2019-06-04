package edu.xufe.entity;

import edu.xufe.entity.cglibproxy.MovieStarCglibProxy;
import org.junit.Test;

/**
 * @ClassName CglibProxyTest
 * @Description TODO
 * @Author greatDistance
 * @Date 2019/5/25 17:28
 * @Version 1.0
 */
public class CglibProxyInterfaceTest {
    /**
     * 通过Cglib接口的方式实现的动态代理
     */
    @Test
    public void testCglibProxyInterface() {
        //原始对象
        Actor actor = new KongFuMovieStar("成龙", "男");
        //成龙要找经纪人了。
        MovieStarCglibProxy proxy = new MovieStarCglibProxy();
        //现在这个成龙变成了有经纪人的成龙对象。
        actor = (Actor) proxy.bind(actor);
        actor.act();
    }
}
