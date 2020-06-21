package org.javatter.javatter.interceptor;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javatter.javatter.annotation.ExcludeInterceptor;
import org.javatter.javatter.auth.LoginUser;
import org.javatter.javatter.entity.User;
import org.javatter.javatter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

public class UserInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    UserService userService;

    // ①コントローラメソッドの実行前に呼ばれる
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // 静的リソースの場合はスキップ
        if (handler instanceof ResourceHttpRequestHandler)
            return true;

        // @ExcludeInterceptorアノテーションがついているcontrollerメソッドはスキップ
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        ExcludeInterceptor exi = AnnotationUtils.findAnnotation(method, ExcludeInterceptor.class);
        if (exi != null)
            return true;

        // URLパラメータ(id)を取得
        Map<String, String> pathVariable = (Map<String, String>) request
                .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Long id = Long.valueOf(pathVariable.get("id"));

        // 認証済ユーザーを取得
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        User currentUser = loginUser.getUser();

        // 自分のページ(認証済ユーザーのページ)でなければリダイレクト処理させる
        if (!(id.equals(currentUser.getId()))) {
            response.sendRedirect("/users");
            return false;
        }

        return true;
    }

    // ②コントローラメソッドの実行後に呼ばれる
    // @Override
    // public void postHandle(HttpServletRequest request, HttpServletResponse
    // response, Object handler,
    // ModelAndView modelAndView) throws Exception {
    // System.out.println("postHandle");
    // }

    // ③リクエスト処理の完了後に呼ばれる
    // @Override
    // public void afterCompletion(HttpServletRequest request, HttpServletResponse
    // response, Object handler, Exception ex)
    // throws Exception {
    // System.out.println("afterCompletion");
    // }
}