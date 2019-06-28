package com.xlx.majiang.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web拦截器
 *
 * @author xielx on 2019/6/23
 */
@Configuration
//@EnableWebMvc
public class WebConfig implements WebMvcConfigurer{
  @Autowired
  private SessionInterceptor sessionInterceptor;

  /*@Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/","/static/");
  }*/

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
     InterceptorRegistration registration = registry.addInterceptor(sessionInterceptor);
     //拦截所有,会排除默认的static resources
    registration.addPathPatterns("/**");

    //不拦截
    /*registration.excludePathPatterns("/");
    registration.excludePathPatterns("/login");
    registration.excludePathPatterns("/css/**");
    registration.excludePathPatterns("/fonts/**");
    registration.excludePathPatterns("/img/**");
    registration.excludePathPatterns("/js/**");
    registration.excludePathPatterns("/layer/**");
    registration.excludePathPatterns("https://github.com/**");*/
  }
}
