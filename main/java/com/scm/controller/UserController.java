package com.scm.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.entity.Contact;
import com.scm.entity.User;
import com.scm.helper.Helper;
import com.scm.repo.ContactRepo;
import com.scm.service.ContactService;
import com.scm.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    ContactService contactserviec;
    @Autowired
    ContactRepo contactRepo;

    // we want the information in all pages

    @GetMapping("/")
    public String getMethodName() {
        return "hello";
    }

    @GetMapping("/dashboard")
    public String userDashboard(Authentication authentication, Model model) {
        String username = Helper.getEmailOfLoginInUser(authentication);
        User user = userService.getUserByEmail(username);
        List<Contact> contact = contactserviec.getByUserId(user.getUserId());

        // Get today's date
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        Date todayStart = Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());

        List<Contact> newContacts = contactRepo.findNewContactsToday(user.getUserId(), todayStart);
        List<Contact> recentlyUpdated = contactRepo.findRecentlyUpdatedToday(user.getUserId(), todayStart);
        System.out.println("Size of the recently Updated" + recentlyUpdated.size());

        model.addAttribute("totalSize", contact.size());
        model.addAttribute("newContactsCount", newContacts.size());
        model.addAttribute("recentlyUpdatedCount", recentlyUpdated.size());
        model.addAttribute("recentContacts", contact);

        return "user/dashboard";
    }

    @GetMapping("/profile")
    public String userProfile(Authentication authentication, Model model) {

        return "user/profile";
    }

    @PostMapping("/profile")
    public String usersProfile(Authentication authentication, Model model) {

        return "user/profile";
    }

}
