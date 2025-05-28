package com.scm.service.impl;

import com.scm.entity.Feedback;
import com.scm.helper.ResourceNotFoundException;
import com.scm.repo.FeedbackRepo;
import com.scm.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackRepo feedbackRepo;
    @Override
    public void savefeedback(Feedback feedback) {
        if(feedback ==null){
            throw new RuntimeException("the form can not be null");
        }
        else{
            feedbackRepo.save(feedback);
        }
    }
}
