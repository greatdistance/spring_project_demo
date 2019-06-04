package edu.xufe.service;

import edu.xufe.dao.StudentDao;
import edu.xufe.entity.Students;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName StudentService
 * @Description TODO
 * @Author greatDistance
 * @Date 2019/5/24 0:50
 * @Version 1.0
 */
@Service
public class StudentService {
    @Resource(name = "student1")
    private Students students;
    @Resource
    private StudentDao studentDao;

    public boolean save() {
        return studentDao.save(students);
    }
}
