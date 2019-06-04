package edu.xufe.entity;

/**
 * @ClassName MovieStar
 * @Description TODO
 * @Author greatDistance
 * @Date 2019/5/25 16:58
 * @Version 1.0
 */
public abstract class MovieStar extends Person implements Actor {
    public MovieStar() {
    }

    public MovieStar(String name, String gender) {
        super(name, gender);
    }
}
