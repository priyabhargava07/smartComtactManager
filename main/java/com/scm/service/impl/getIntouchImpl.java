package com.scm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scm.entity.getInTouch;
import com.scm.repo.getInTouchRepo;
import com.scm.service.GetInTouchService;

@Service
public class getIntouchImpl implements GetInTouchService {
    @Autowired
    private getInTouchRepo repo;

    @Override
    public getInTouch save(getInTouch getInTouch) {
        return repo.save(getInTouch);
    }

}
