package edu.xufe.demo01;


import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.DriverManager;

/**
 * @ClassName JdbcDemo01
 * @Description JDBC模板的使用
 * @Author greatDistance
 * @Date 2019/6/4 18:40
 * @Version 1.0
 */
public class JdbcDemo01 {
    @Test
    public void demo01(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql:///springdemo07");
        dataSource.setUsername("root");
        dataSource.setPassword("mysql");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("insert into account value (null,?,?)","余永涛",10000d);
    }
}
