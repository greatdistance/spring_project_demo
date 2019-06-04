package edu.xufe.entity;

/**
 * @ClassName AbstractActor
 * @Description 演员抽象类 用户Cglib代理
 * @Author greatDistance
 * @Date 2019/5/25 17:52
 * @Version 1.0
 */
public abstract class AbstractActor extends Person {
    public AbstractActor() {
    }

    public AbstractActor(String name, String gender) {
        super(name, gender);
    }

    /**
     * 演戏的抽象方法
     */
    public abstract void act();
}
