package com.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.scm.entity.Contact;
import com.scm.service.ContactService;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }

    @Autowired
    private ContactService contactService;
   
    @GetMapping("/contacts/{contactId}")
    public Contact getContact(@PathVariable("contactId") String contctId){
        return contactService.getById(contctId);
    }

}
