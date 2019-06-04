package edu.xufe.entity;

/**
 * @ClassName Person
 * @Description TODO
 * @Author greatDistance
 * @Date 2019/5/25 16:49
 * @Version 1.0
 */
public class Person {
    /**
     * 姓名
     */
    protected String name;
    /**
     * 性别
     */
    protected String gender;

    public Person() {
    }

    public Person(String name, String gender) {
        this.name = name;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
