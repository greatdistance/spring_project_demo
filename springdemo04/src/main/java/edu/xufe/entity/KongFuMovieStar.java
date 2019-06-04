package edu.xufe.entity;

/**
 * @ClassName KongFuMovieStar
 * @Description TODO
 * @Author greatDistance
 * @Date 2019/5/25 17:01
 * @Version 1.0
 */
public class KongFuMovieStar extends MovieStar {
    public KongFuMovieStar() {
    }

    public KongFuMovieStar(String name, String gender) {
        super(name, gender);
    }

    @Override
    public void act() {
        System.out.println(this.name + "是功夫巨星..." + "正在拍戏中。。。。");
    }
}
