package com.xlx.majiang.common.validate.image;

import com.alibaba.fastjson.JSON;
import com.xlx.majiang.common.constant.ValidateConstant;
import com.xlx.majiang.system.dto.ResultDTO;
import com.xlx.majiang.system.exception.ValidateCodeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 注册过程图片验证码的过滤器，验证码错误就不在验证表单数据了
 *
 * @author xielx at 2020/2/11 20:34
 */
@Slf4j
public class ImageCodeFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String method = request.getMethod();
        if (StringUtils.contains("/user/register",uri) && StringUtils.equalsIgnoreCase("post",method)){
            try {
                validate(new ServletWebRequest(request));
            }catch (ValidateCodeException e){
                log.error("校验验证图片码异常:[{}]",e.getMessage());
                // 需要处理捕获的异常,跳转
               // request.getRequestDispatcher("/login.html").forward(request,response);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json;charset=UTF-8");
                try(PrintWriter out = response.getWriter()){
                    out.write(JSON.toJSONString(ResultDTO.errorOf(e)));
                    return;
                }
            }
        }
        log.info(" 验证码正确,执行下一个过滤链");
        // 验证码正确,执行下一个过滤链
        filterChain.doFilter(request,response);
        
    }
    
    /**
     * 校验验证码
     * @param servletWebRequest 请求
     * @throws ServletRequestBindingException 异常
     */
    private void validate(ServletWebRequest servletWebRequest) throws ServletRequestBindingException {
        HttpServletRequest request = servletWebRequest.getRequest();
        HttpSession session = request.getSession();
        ImageCode imageCode = (ImageCode) session.getAttribute(ValidateConstant.SESSION_KEY);
        String code = ServletRequestUtils.getStringParameter(request, "imageCode");
        log.info("前端图片验证码:[{}]",code);
        
        // 校验异常
        if (StringUtils.isEmpty(code)){
            throw new ValidateCodeException("验证码不能为空");
        }
        if (imageCode.isExpired()){
            session.removeAttribute(ValidateConstant.SESSION_KEY);
            throw new ValidateCodeException("验证码过期");
        }
        if (!StringUtils.equalsIgnoreCase(code,imageCode.getCode())){
            throw new ValidateCodeException("验证码错误");
        }
        
        // 验证码正确,删除session存储的旧值
        session.removeAttribute(ValidateConstant.SESSION_KEY);
    }
}
