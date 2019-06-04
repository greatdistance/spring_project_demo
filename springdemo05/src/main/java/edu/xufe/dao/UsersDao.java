package edu.xufe.dao;

/**
 * @InterfaceName UsersDao
 * @Description 模拟演示 Users dao
 * @Author greatDistance
 * @Date 2019/5/28 17:37
 * @Version 1.0
 */
public interface UsersDao {
    /**
     * 增
     */
    void save();

    /**
     * 改
     *
     */
    void update();



    /**
     * 删
     * @return
     */
    boolean delete();

    /**
     * 查
     */
    void find();
}
