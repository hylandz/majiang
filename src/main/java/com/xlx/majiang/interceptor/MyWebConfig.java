package com.xlx.majiang.interceptor;

import com.xlx.majiang.common.validate.image.ImageCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
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
public class MyWebConfig implements WebMvcConfigurer{
  
  @Autowired
  private SessionInterceptor sessionInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
     InterceptorRegistration registration = registry.addInterceptor(sessionInterceptor);
     //拦截所有,会排除默认的static resources
    registration.excludePathPatterns("/static/**").addPathPatterns("/**");
  }
  
  @Bean
  public FilterRegistrationBean<ImageCodeFilter> imageCodeFilterRegistrationBean(){
    FilterRegistrationBean<ImageCodeFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new ImageCodeFilter());
    registrationBean.addUrlPatterns("/login");
    return registrationBean;
  }
  
}
