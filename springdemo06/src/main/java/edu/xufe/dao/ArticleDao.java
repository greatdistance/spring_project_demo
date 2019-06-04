package edu.xufe.dao;

/**
 * @ClassName ArticleDao
 * @Description TODO
 * @Author greatDistance
 * @Date 2019/5/29 0:11
 * @Version 1.0
 */
public class ArticleDao {
    public void save() {
        System.out.println("增加文章信息业务逻辑代码");
    }

    public boolean delete() {
        System.out.println("删除文章信息业务逻辑代码");
        return true;
    }

    public void update() {
        System.out.println("更新文章信息业务逻辑代码");

    }

    public void find() {
        System.out.println("查询文章信息业务逻辑代码");
        // 演示异常通知时打开
        // int i = 10 / 0;
    }
}
