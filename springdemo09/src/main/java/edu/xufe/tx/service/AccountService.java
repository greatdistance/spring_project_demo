package edu.xufe.tx.service;

/**
 * @Author greatDistance
 */
public interface AccountService {
    /**
     * 转账
     * @param form 转出账号
     * @param to 转入账号
     * @param money 金额
     */
    void transfer(String form,String to,Double money);
}
