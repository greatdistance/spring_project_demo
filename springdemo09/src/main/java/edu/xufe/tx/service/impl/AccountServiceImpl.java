package edu.xufe.tx.service.impl;

import edu.xufe.tx.dao.AccountDao;
import edu.xufe.tx.service.AccountService;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

/**
 * @ClassName AccountServiceImpl
 * @Description TODO
 * @Author greatDistance
 * @Date 2019/6/4 22:41
 * @Version 1.0
 */
@Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
public class AccountServiceImpl implements AccountService {
    /**
     * 注入Dao
     */
    private AccountDao accountDao;

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }


    @Override
    public void transfer(String form, String to, Double money) {
        accountDao.outMoney(form, money);
        // 人为异常，测试异常转账时打开
        int a = 5 / 0;
        accountDao.inMoney(to, money);
    }
}
