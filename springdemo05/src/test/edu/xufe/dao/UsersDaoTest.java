package edu.xufe.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UsersDaoTest {
    @Resource(name = "usersDao")
    private UsersDao usersDao;

    @Test
    public void test() {
        usersDao.save();
        usersDao.update();
        usersDao.delete();
        usersDao.find();
    }
}