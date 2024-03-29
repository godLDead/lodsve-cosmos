package message.security.config;

import message.security.annotation.resolver.CurrentAccountResolver;
import message.security.core.AuthzInterceptor;
import message.security.core.LoginInterceptor;
import message.security.service.Authz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * security模块的配置，业务模块使用时，import到spring web的配置即可.
 *
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0, 2016-2-18 14:27
 */
@Configuration
@ComponentScan(basePackages = "message.security")
public class SecurityConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private Authz authz;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 先执行 是否登录的拦截器
        registry.addInterceptor(new LoginInterceptor(authz));
        // 后执行 是否有权限的拦截器
        registry.addInterceptor(new AuthzInterceptor(authz));
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new CurrentAccountResolver());
    }
}
