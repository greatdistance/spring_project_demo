package edu.xufe.demo01;

import edu.xufe.demo01.entity.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @ClassName JdbcDemo03
 * @Description TODO
 * @Author greatDistance
 * @Date 2019/6/4 20:44
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class JdbcDemo03 {
    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    /**
     * 增
     */
    @Test
    public void testSave() {
        jdbcTemplate.update("insert into account value (null,?,?)", "如花1", 10000d);
    }

    /**
     * 修改
     */
    @Test
    public void testUpdate() {
        jdbcTemplate.update("update account set name=?,money=? where id =?", "修改如花", 8000d, 1);
    }

    /**
     * 删除
     */
    @Test
    public void testDelete() {
        jdbcTemplate.update("delete from account where id=?", 1);
    }

    /**
     * 查询单个
     */
    @Test
    public void testQuery() {
        String name = jdbcTemplate.queryForObject("select name from account where id = ?", String.class, 1);
        Long count = jdbcTemplate.queryForObject("select count(*) from account", Long.class);
        System.out.println("name = " + name);
        System.out.println("count = " + count);
    }

    /**
     * 封装一个结果集
     */
    class MyRowMapper implements RowMapper<Account> {
        @Override
        public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
            Account account = new Account();
            account.setId(rs.getInt("id"));
            account.setName(rs.getString("name"));
            account.setMoney(rs.getDouble("money"));
            return account;
        }
    }

    /**
     * 查询所有属性
     * 封装到一个对象中
     */
    @Test
    public void testQueryObject(){
        Account account = jdbcTemplate.queryForObject("select * from account where id = ?", new MyRowMapper(), 1);
        System.out.println(account);
    }

    /**
     * 查询多条记录
     */
    @Test
    public void testQueryObjectList(){
        List<Account> list = jdbcTemplate.query("select * from account", new MyRowMapper());
        for (Account account : list) {
            System.out.println(account);
        }
    }
}
