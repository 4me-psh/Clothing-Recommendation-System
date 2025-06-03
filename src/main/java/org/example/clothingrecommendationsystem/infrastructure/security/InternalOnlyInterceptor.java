package org.example.clothingrecommendationsystem.infrastructure.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class InternalOnlyInterceptor implements HandlerInterceptor {

    @Value("${jwt.registration-token}")
    private String internalToken;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if (!(handler instanceof HandlerMethod method)) {
            return true;
        }

        if (method.hasMethodAnnotation(InternalOnly.class)) {
            String auth = request.getHeader("Authorization");
            String expected = "Bearer " + internalToken;

            if (!expected.equals(auth)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden: internal access only");
                return false;
            }
        }

        return true;
    }
}

