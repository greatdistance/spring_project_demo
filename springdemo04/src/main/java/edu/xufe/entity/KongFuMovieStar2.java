package edu.xufe.entity;

/**
 * @ClassName KongFuMovieStar2
 * @Description TODO
 * @Author greatDistance
 * @Date 2019/5/25 18:03
 * @Version 1.0
 */
public class KongFuMovieStar2 extends MoveStar2 {
    public KongFuMovieStar2() {
    }

    public KongFuMovieStar2(String name, String gender) {
        super(name, gender);
    }

    @Override
    public void act() {
        System.out.println(this.name + "是功夫巨星..." + "正在拍戏中。。。。");
    }
}
