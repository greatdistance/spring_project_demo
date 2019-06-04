package edu.xufe.dao;

import edu.xufe.entity.Students;

/**
 * @ClassName StudentDao
 * @Description Student dao 演示使用 无实际含义
 * @Author greatDistance
 * @Date 2019/5/23 23:09
 * @Version 1.0
 */
public interface StudentDao {
    /**
     * 保存学生
     *
     * @param students
     * @return boolean
     */
    boolean save(Students students);
}
