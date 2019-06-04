package edu.xufe.tx.dao;

/**
 * @Author greatDistance
 */
public interface AccountDao {
    /**
     * 转出
     * @param from
     * @param money
     */
    void outMoney(String from ,Double money);

    /**
     * 转入
     * @param to
     * @param money
     */
    void inMoney(String to ,Double money);
}
