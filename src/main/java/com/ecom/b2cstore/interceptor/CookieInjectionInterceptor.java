package com.ecom.b2cstore.interceptor;

import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.ecom.b2cstore.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CookieInjectionInterceptor implements HandlerInterceptor {
    // This interceptor can be expanded in the future if needed
    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {
        // Inject cookies into the request
        String guestUUID = CookieUtil.getCookie(request, "b2c_guest_uuid");

        if (guestUUID == null) {
            // Generate a new UUID for the guest user
            guestUUID = UUID.randomUUID().toString();
            CookieUtil.addCookie(response, "b2c_guest_uuid", guestUUID, 60 * 60 * 24 * 30); // 30 days
        }

        request.setAttribute("guestUUID", guestUUID);
        return true;
    }

}
