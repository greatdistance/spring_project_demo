package edu.xufe.convert;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;

/**
 * @ClassName MyCustomDateEditorRegistrar
 * @Description 自定义了一个注册器类
 * @Author greatDistance
 * @Date 2019/5/24 16:22
 * @Version 1.0
 */

public class MyCustomDateEditorRegistrar implements PropertyEditorRegistrar {

    private String format;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public void registerCustomEditors(PropertyEditorRegistry registry) {
        registry.registerCustomEditor(java.util.Date.class, new MyDateConvert(format));
    }

}

