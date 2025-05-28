package com.scm.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.scm.entity.User;
import com.scm.entity.getInTouch;
import com.scm.forms.UserForms;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.service.GetInTouchService;
import com.scm.service.ImageService;
import com.scm.service.UserService;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

    @Autowired
    ImageService imageService;

    private Logger logger = LoggerFactory.getLogger(PageController.class);

    @Autowired
    private UserService userService;

    @Autowired
    GetInTouchService getInTouchService;

    @GetMapping("/")
    public String indexPage() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String first(Model model) {
        model.addAttribute("title", "Home - Smart Contact Manager");
        model.addAttribute("channel", "lern java ");
        model.addAttribute("github", "priyabhargava07");
        return "hello";
    }

    @PostMapping("/touch")
    public String getInTouch(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("message") String message, Model model) {

        System.out.println("this method et intouch is being called ");

        model.addAttribute("successMessage", "Your message has been sent successfully!");

        getInTouch getInTouch = new getInTouch();
        getInTouch.setEmail(email);
        getInTouch.setMessage(message);
        getInTouch.setName(name);
        System.out.println(getInTouch);
        getInTouchService.save(getInTouch);
        return "redirect:/contact";

    }

    @GetMapping("/about")
    public String AboutPage() {
        return "about";
    }

    @GetMapping("/service")
    public String ServicePage() {
        return "service";
    }

    @GetMapping("/contact")
    public String ContactPage(Model model) {
        model.addAttribute("successMessage", "Your message has been sent successfully!");

        return "contact";
    }

    @GetMapping("/login")
    public String LoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String RegisterPage(Model model) {
        UserForms userForm = new UserForms();
        // userForm.setName("priya");
        // userForm.setAbout("hello");
        // userForm.setEmail("hello@gmail.com");
        // userForm.setPhoneNumber("9876554");
        // System.out.println("register is called ");
        model.addAttribute("userForm", userForm);
        return "register";
    }

    @PostMapping("/do-register")
    public String processRegister(@Validated @ModelAttribute("userForm") UserForms userform,
            BindingResult rBindingResult, HttpSession session, Model model) {
        System.out.println("do register is called");
        System.out.println(userform.toString());
        if (rBindingResult.hasErrors()) {
            System.out.println("here are some errors");
            model.addAttribute("userForm", userform);

            return "register";
        }
        User newUser = new User();
        newUser.setName(userform.getName());
        newUser.setAbout(userform.getAbout());
        newUser.setEmail(userform.getEmail());
        newUser.setPassword(userform.getPassword());
        newUser.setPhoneNumber(userform.getPhoneNumber());
        newUser.setEnabled(false);
        newUser.setProfilePic(
                "data:image/jpeg;base64,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");
        Message message = Message.builder().content("Registration Successful").type(MessageType.blue).build();
        System.out.println(message.toString());
        if (userform.getProfileImage() != null && !userform.getProfileImage().isEmpty()) {
            String filename = UUID.randomUUID().toString();
            String fileURL = imageService.uploadImage(userform.getProfileImage(), filename);
            newUser.setProfilePic(fileURL);
            newUser.setCloudinaryImagePublicId(filename);

        } else {
            logger.info("file is empty");
        }
        session.setAttribute("message", message);
        userService.saveUser(newUser);
        System.out.println("register method is called ");
        return "redirect:/register";
    }
}
