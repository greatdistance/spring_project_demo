package edu.xufe.demo01;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @ClassName JdbcDemo02
 * @Description TODO
 * @Author greatDistance
 * @Date 2019/6/4 19:08
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class JdbcDemo02 {
    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Test
    public void demo02() {
        jdbcTemplate.update("insert into account value (null,?,?)", "如花148", 10000d);
    }
}

