package edu.xufe.entity;

/**
 * @ClassName Demo
 * @Description TODO
 * @Author greatDistance
 * @Date 2019/5/24 19:48
 * @Version 1.0
 */
public class Demo {
    public static void main(String[] args) {
        String s1 = "hello";
        String s2 = s1;
        String s3 = new String("hello");
        System.out.println("s1.hashCode() = " + s1.hashCode());
        System.out.println("s2.hashCode() = " + s2.hashCode());
        System.out.println("s3.hashCode() = " + s3.hashCode());
        String s4 = "通话";
        String s5 = "重地";
        System.out.println("s4.hashCode() = " + s4.hashCode());
        System.out.println("s5.hashCode() = " + s5.hashCode());
    }
}
