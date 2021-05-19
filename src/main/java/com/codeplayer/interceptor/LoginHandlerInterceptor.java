package com.codeplayer.interceptor;

import com.codeplayer.entity.User;
import com.codeplayer.service.NotificationService;
import com.codeplayer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;
    @Autowired
    private NotificationService notificationService;
    /**
     * 在目标方式执行之前执行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    List<User> user = userService.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user.get(0));
                        //未读数
                        Integer unreadCount = notificationService.unreadCount(user.get(0).getUserId());
                        request.getSession().setAttribute("unreadCount", unreadCount);
                    }
                    break;
                }
            }
        }
        return true;
    }
}

