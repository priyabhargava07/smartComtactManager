package com.scm.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entity.User;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.repo.UserRepo;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserRepo userrepo;

    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam("token") String token, HttpSession session) {
        User user = userrepo.findByEmailToken(token).orElse(null);
        if (user != null) {
            if (user.getEmailToken().equals(token)) {
                user.setEmailVerified(true);
                user.setEnabled(true);
                userrepo.save(user);
                logger.info("Verified email token: {}", token);
                session.setAttribute("message",
                        Message.builder()
                                .content("Email is verified! Successfully")
                                .type(MessageType.blue)
                                .build());
                return "success"; // Ensure this view exists
            }
            logger.info("Email token already used: {}", token);
        } else {
            logger.warn("User not found for token: {}", token);
        }

        session.setAttribute("message",
                Message.builder()
                        .content("Email is not verified! Token is not associated with this user")
                        .type(MessageType.red)
                        .build());
        return "error"; // Ensure this view exists
    }
}
