package edu.xufe.dao;

/**
 * @ClassName Usb
 * @Description Usb设备接口
 * @Author greatDistance
 * @Date 2019/5/23 23:09
 * @Version 1.0
 */
public interface Usb {
    /**
     * 读功能
     */
    void read();

    /**
     * 写功能
     *
     * @param data 写的数据
     */
    void write(String data);
}
