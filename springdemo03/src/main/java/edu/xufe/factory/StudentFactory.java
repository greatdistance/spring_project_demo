package edu.xufe.factory;

import edu.xufe.entity.Students;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @ClassName StudentFactory
 * @Description 学生工厂类
 * @Author greatDistance
 * @Date 2019/5/23 22:58
 * @Version 1.0
 */
public class StudentFactory {

    public static Students getStudentsInstance(int sid, String username, String gender, Date birthday) {
        Students student = null;
        try {
            // 获得学生实体
            student = (Students) Class.forName("edu.xufe.entity.Students").newInstance();
            // 获得setId方法
            Method method = student.getClass().getMethod("setSid", int.class);
            // 调用setId方法
            method.invoke(student, sid);
            method = student.getClass().getMethod("setUsername", String.class);
            method.invoke(student, username);
            method = student.getClass().getMethod("setGender", String.class);
            method.invoke(student, gender);
            method = student.getClass().getMethod("setBirthday", Date.class);
            method.invoke(student, birthday);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return student;
    }
}
