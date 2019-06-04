package edu.xufe.entity;

/**
 * @ClassName Users
 * @Description TODO
 * @Author greatDistance
 * @Date 2019/5/22 23:44
 * @Version 1.0
 */
public class Users {
    /**
     * 用户编号
     */
    private int uid;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    public Users() {

    }

    public Users(int uid, String username, String password) {
        this.uid = uid;
        this.username = username;
        this.password = password;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Users{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
