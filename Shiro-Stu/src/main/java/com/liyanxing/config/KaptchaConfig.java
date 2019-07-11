package com.liyanxing.config;/**
 * @author xiaoliu
 * @Date 2019/7/10 17:33
 */

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sun.misc.ObjectInputFilter;

import java.util.Properties;

/**
 * @Author xiaoliu
 * @Date 2019/7/10 17:33
 * @Description 验证码配置类
 */
@Configuration
public class KaptchaConfig {

    @Bean
    public DefaultKaptcha getKaptcha(){
        /*1、创建对象*/
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();

        /*2、创建properties对象*/
        Properties properties = new Properties();
        

        /*3、给properties对象设置属性值*/
        properties.setProperty("kaptcha.border", "yes");
        properties.setProperty("kaptcha.border.color", "105,179,90");
        properties.setProperty("kaptcha.textproducer.font.color", "blue");
        properties.setProperty("kaptcha.image.width", "110");
        properties.setProperty("kaptcha.image.height", "40");
        properties.setProperty("kaptcha.textproducer.font.size", "30");
        properties.setProperty("kaptcha.session.key", "code");
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");

        /*4、属性设置结束以后、创建config对象*/


        return null;
    }
}
