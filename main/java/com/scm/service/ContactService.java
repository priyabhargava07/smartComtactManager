package com.scm.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.scm.entity.Contact;
import com.scm.entity.User;

public interface ContactService {

    Contact saveContact(Contact contact);

    Contact updateContact(Contact contact);

    List<Contact>getAll();
    Contact getById(String id);

    void deleteContact(String id);

   
    List<Contact> getByUserId(String userId);
    Page<Contact>searchByName(String nameKeyword,int size,int page,String sortBy,String orderm,User user);
    Page<Contact>searchByEmail(String emailKeyword,int size,int page,String sortBy,String order,User user);
    Page<Contact>searchByPhoneNumber(String phkeyword,int size,int page,String sortBy,String order, User user);

    
    Page<Contact> getByUser(User user,int page , int size , String sortBy , String direction);



}
