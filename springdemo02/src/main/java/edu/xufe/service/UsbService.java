package edu.xufe.service;

import edu.xufe.dao.Usb;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName UsbService
 * @Description TODO
 * @Author greatDistance
 * @Date 2019/5/23 23:24
 * @Version 1.0
 */
@Service("usbService")
public class UsbService {
    @Resource(name = "device1")
    private Usb device;

    public Usb getDevice() {
        return device;
    }

    public void setDevice(Usb device) {
        this.device = device;
    }

    /**
     * 读功能
     */
    public void read() {
        device.read();
    }

    /**
     * 写功能
     *
     * @param data 写的数据
     */
    public void write(String data) {
        device.write(data);
    }
}
