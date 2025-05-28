package com.scm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.entity.User;
import com.scm.helper.Helper;
import com.scm.service.UserService;

@ControllerAdvice // this will make the controller to run for every request
public class RootController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserService userService;

    @ModelAttribute
    public void addLoggedUserInformation(Authentication authentication, Model model) {
        if (authentication == null) {
            return;
        }
        logger.info("fetching the user from the eamil");
        String name = Helper.getEmailOfLoginInUser(authentication);
        logger.info("fecthing the user details from the database");
        User user = userService.getUserByEmail(name);
        if (user == null) {
            logger.info("the user is not present");
        }
        logger.info("sending the data to the model view");
        model.addAttribute("loggedInUser", user);

    }

}
