package edu.xufe.convert;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @ClassName MyDateConvert
 * @Description 自定义字符串转日期
 * @Author greatDistance
 * @Date 2019/5/24 16:19
 * @Version 1.0
 */
public class MyDateConvert extends PropertyEditorSupport {
    /**
     * 字符串格式
     * yyyy-MM-dd yyyy/MM/dd
     * 或是其他 应由外部传入
     */
    private String format;

    public MyDateConvert(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        SimpleDateFormat sdf = new SimpleDateFormat(this.format);
        try {
            this.setValue(sdf.parse(text));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
