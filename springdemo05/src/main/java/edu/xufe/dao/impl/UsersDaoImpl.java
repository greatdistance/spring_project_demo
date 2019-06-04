package edu.xufe.dao.impl;

import edu.xufe.dao.UsersDao;

/**
 * @ClassName UsersDaoImpl
 * @Description TODO
 * @Author greatDistance
 * @Date 2019/5/28 17:42
 * @Version 1.0
 */
public class UsersDaoImpl implements UsersDao {
    @Override
    public void save() {
        System.out.println("增加用户信息业务逻辑代码");
    }

    @Override
    public boolean delete() {
        System.out.println("删除用户信息业务逻辑代码");
        return true;
    }

    @Override
    public void update() {
        System.out.println("更新用户信息业务逻辑代码");

    }

    @Override
    public void find() {
        System.out.println("查询用户信息业务逻辑代码");
        // 演示异常通知时打开
       // int i = 10 / 0;
    }
}
