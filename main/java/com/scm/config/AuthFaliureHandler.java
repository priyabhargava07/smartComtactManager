package com.scm.config;

import java.io.IOException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.scm.helper.Message;
import com.scm.helper.MessageType;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthFaliureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
                HttpSession session = request.getSession();
    if(exception instanceof DisabledException){
        System.out.println("the method of disabled is called ");
        session.setAttribute("message",
         Message.
         builder()
             .content("Your account is disabled ,Email with verification link is send ")
            .type(MessageType.red)
            .build());

              response.sendRedirect("/login?error=disabled");
       }
       else {
        session.setAttribute("message", Message.builder()
                .content("Invalid username or password")
                .type(MessageType.red)
                .build());
        response.sendRedirect("/login?error=true");
    }
    }

}
