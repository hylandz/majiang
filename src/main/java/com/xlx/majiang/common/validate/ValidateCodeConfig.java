package com.xlx.majiang.common.validate;

import com.xlx.majiang.common.validate.image.ImageCodeGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 验证码配置
 *
 * @author xielx at 2020/2/11 22:16
 */
@Configuration
public class ValidateCodeConfig {
    
    
    /**
     * 为ValidateCodeController实现注入
     * 注解@ConditionalOnMissingBean,会先依据name属性值检测是否存在,不存在才执行这个方法
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    public ValidateGenerator imageCodeGenerator(){
        return new ImageCodeGenerator();
    }
}
