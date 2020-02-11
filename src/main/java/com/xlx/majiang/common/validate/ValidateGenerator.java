package com.xlx.majiang.common.validate;

import com.xlx.majiang.common.validate.image.ImageCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码生成接口:提供各种生成方法
 *
 * @author xielx at 2020/2/11 10:24
 */
public interface ValidateGenerator {
    
    /**
     * 验证码生成方法
     * @param servletWebRequest 包含ServletRequest和ServletResponse
     * @return ImageCode
     */
    ImageCode generate(ServletWebRequest servletWebRequest);
}
