package com.foodcourt.campusfoodcourt.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String redirectURL = request.getContextPath();

        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();

            switch (role) {
                case "ROLE_ADMIN":
                case "ADMIN":
                    redirectURL += "/admin/menu";
                    break;
                case "ROLE_STUDENT":
                case "STUDENT":
                    redirectURL += "/menu";
                    break;
                case "ROLE_TEACHER":
                case "TEACHER":
                    redirectURL += "/menu";
                    break;
                default:
                    redirectURL += "/access-denied";
                    break;
            }
            break; // break after first match
        }

        response.sendRedirect(redirectURL);
    }
}
