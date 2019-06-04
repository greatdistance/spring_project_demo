package edu.xufe.entity;

import edu.xufe.entity.jdkproxy.MovieStarJdkProxy;
import org.junit.Test;

/**
 * @ClassName JdkProxyTest
 * @Description TODO
 * @Author greatDistance
 * @Date 2019/5/25 17:12
 * @Version 1.0
 */
public class JdkProxyTest {
    /**
     * jdk原生动态代理
     * 缺点 只能通过实现接口来实现动态
     */
    @Test
    public void testJdkProxy() {
        Actor actor = new KongFuMovieStar("成龙", "男");
        MovieStarJdkProxy proxy = new MovieStarJdkProxy();
        //现在这个成龙变成了有经纪人的成龙对象。
        actor = (Actor) proxy.bind(actor);
        actor.act();
    }

}
