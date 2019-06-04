package edu.xufe.dao.impl;

import edu.xufe.dao.StudentDao;
import edu.xufe.entity.Students;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @ClassName StudentDaoImpl
 * @Description TODO
 * @Author greatDistance
 * @Date 2019/5/24 0:46
 * @Version 1.0
 */
@Repository
public class StudentDaoImpl implements StudentDao {

    @Override
    public boolean save(Students students) {
        System.out.println("保存学生信息" + students);
        return false;
    }
}
