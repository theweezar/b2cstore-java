package com.ecom.b2cstore.util;

import java.util.Arrays;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {
    public static String getCookie(HttpServletRequest request, String name) {
        // Implementation here
        Cookie[] cookies = request.getCookies();
        return cookies == null
                ? null
                : Arrays.stream(cookies)
                        .filter(c -> name.equals(c.getName()))
                        .map(Cookie::getValue)
                        .findAny()
                        .orElse(null);
    }

    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }
}
