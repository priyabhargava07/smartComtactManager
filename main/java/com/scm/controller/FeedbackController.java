package com.scm.controller;

import com.scm.entity.Feedback;
import com.scm.forms.FeedbackForm;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.service.FeedbackService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    FeedbackForm defaultForm = new FeedbackForm("name", "email@gmail.com", "messge");

    @GetMapping("/page")
    public String userFeedback(Model model) {
        model.addAttribute("feedback", defaultForm);
        return "user/feedbackPage";
    }

    @PostMapping("/feedback/submit")
    public String saveFeedback(@Valid @ModelAttribute FeedbackForm feedbackForm, BindingResult result,
            HttpSession session) {
        Feedback feedback = new Feedback();
        if (result.hasErrors()) {
            session.setAttribute("message", Message.builder()
                    .content("Please correct the following errors")
                    .type(MessageType.red)
                    .build());
        } else if (feedbackForm.getName().equals("name") || feedbackForm.getEmail().equals("email@gamil.com")
                || feedbackForm.getMessage().equals("message")) {
            session.setAttribute("message",
                    Message.builder()
                            .content("Name,messgae,email can not be the default one")
                            .type(MessageType.red).build());
        }

        else {
            feedback.setEmail(feedbackForm.getEmail());
            feedback.setName(feedbackForm.getName());
            feedback.setMessage(feedbackForm.getMessage());
            feedbackService.savefeedback(feedback);
            session.setAttribute("message", Message.builder()
                    .content("You have successfully submitted the feedback")
                    .type(MessageType.yellow)
                    .build());
        }
        return "redirect:/page";
    }
}
