package edu.xufe.dao.impl;

import edu.xufe.dao.Usb;


/**
 * @ClassName MoveDisk
 * @Description 移动硬盘
 * @Author greatDistance
 * @Date 2019/5/23 23:18
 * @Version 1.0
 */
public class MoveDisk implements Usb {
    @Override
    public void read() {
        System.out.println("移动硬盘读数据");
    }

    @Override
    public void write(String data) {
        System.out.println("移动硬盘写数据" + data);
    }
}
