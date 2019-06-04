package edu.xufe.entity;

import edu.xufe.entity.cglibproxy.MovieStarCglibProxy;
import org.junit.Test;

/**
 * @ClassName CgilbProxyAbstractClassTest
 * @Description TODO
 * @Author greatDistance
 * @Date 2019/5/27 14:49
 * @Version 1.0
 */
public class CglibProxyAbstractClassTest {
    /**
     * 测试Cglib抽象类实现的动态代理
     */
    @Test
    public void testKongFuStar() {
        AbstractActor jack = new KongFuMovieStar2("成龙", "男");
        MovieStarCglibProxy proxy = new MovieStarCglibProxy();
        jack = (AbstractActor) proxy.bind(jack);
        jack.act();
    }
}
