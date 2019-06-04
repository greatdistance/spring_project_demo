package edu.xufe.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ClassName Students
 * @Description TODO
 * @Author greatDistance
 * @Date 2019/5/23 22:45
 * @Version 1.0
 */
@Component("zhangliu")
public class Students {
    @Value("1605990418")
    private int sid;
    @Value("赵六")
    private String username;
    @Value("女")
    private String gender;
    @Value("1996-11-20")
    private Date birthday;

    public Students(int sid, String username, String gender, Date birthday) {
        this.sid = sid;
        this.username = username;
        this.gender = gender;
        this.birthday = birthday;
    }

    public Students() {
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "Students{" +
                "sid=" + sid +
                ", username='" + username + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
