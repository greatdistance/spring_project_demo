package edu.xufe.dao.impl;

import edu.xufe.dao.Usb;

/**
 * @ClassName UsbDisk
 * @Description U盘
 * @Author greatDistance
 * @Date 2019/5/23 23:18
 * @Version 1.0
 */
public class UsbDisk implements Usb {
    @Override
    public void read() {
        System.out.println("U盘读数据");
    }

    @Override
    public void write(String data) {
        System.out.println("U盘写数据" + data);
    }
}
