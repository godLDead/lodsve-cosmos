package message.security.core;

import message.security.annotation.NeedAuthz;
import message.security.service.Authz;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 鉴权的拦截器.
 *
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2014-12-7 14:53
 */
public class AuthzInterceptor extends HandlerInterceptorAdapter {
    private Authz authz;

    public AuthzInterceptor(Authz authz) {
        this.authz = authz;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return super.preHandle(request, response, handler);
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //获取注解
        NeedAuthz ac = method.getAnnotation(NeedAuthz.class);

        if (ac == null) {
            return super.preHandle(request, response, handler);
        }

        //判断是否有权限
        Account account = LoginAccountHolder.getCurrentAccount();
        if (account == null) {
            throw new RuntimeException("not login!");
        }

        if (this.authz.authz(account, ac.value())) {
            return super.preHandle(request, response, handler);
        } else {
            throw new RuntimeException("authz failure! user id is:{" + account.getId() + "}!");
        }
    }
}
