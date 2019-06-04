package edu.xufe.tx.dao.impl;

import edu.xufe.tx.dao.AccountDao;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * @ClassName AccountDaoImpl
 * @Description TODO
 * @Author greatDistance
 * @Date 2019/6/4 22:44
 * @Version 1.0
 */
public class AccountDaoImpl extends JdbcDaoSupport implements AccountDao {
    @Override
    public void outMoney(String from, Double money) {
        String sql = "update account set money = money - ? where name=?";
        this.getJdbcTemplate().update(sql,money,from);
    }

    @Override
    public void inMoney(String to, Double money) {
        String sql = "update account set money = money + ? where name=?";
        this.getJdbcTemplate().update(sql,money,to);
    }
}
