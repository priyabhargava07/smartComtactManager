package com.scm.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.entity.User;
import com.scm.helper.AppConstants;
import com.scm.helper.Helper;
import com.scm.helper.ResourceNotFoundException;
import com.scm.repo.UserRepo;
import com.scm.service.EmailService;
import com.scm.service.UserService;

@Service
public class UserServiceImpl implements UserService {
   @Autowired
   private UserRepo userRepo;
   @Autowired
   private PasswordEncoder passwordEncoder;
   @Autowired
   private EmailService emailService;
   @Autowired
   private Helper helper;
   private Logger logger = LoggerFactory.getLogger(this.getClass());

   @Override
   public User saveUser(User user) {
      String userId = UUID.randomUUID().toString();
      user.setUserId(userId);
      user.setPassword(passwordEncoder.encode(user.getPassword()));

      // we will set the user role
      user.setRoleList(List.of(AppConstants.ROLE_USER));
      logger.info(user.getProvider().toString());
      String emailToken = UUID.randomUUID().toString();
      user.setEmailToken(emailToken);
      User saveUser = userRepo.save(user);
      String emailLink = helper.getLinkForEmailVerification(emailToken);
      emailService.sendEmail(user.getEmail(), "Verify Account : Smart Contact Manager ", emailLink);
      return saveUser;
   }

   @Override
   public Optional<User> getUserById(String id) {
      return userRepo.findById(id);
   }

   @Override
   public Optional<User> updateUser(User user) {

      User user2 = userRepo.findById(user.getUserId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
      user2.setName(user.getName());
      user2.setAbout(user.getAbout());
      user2.setEmail(user.getEmail());
      user2.setPassword(user.getPassword());
      user2.setPhoneNumber(user.getPhoneNumber());
      user2.setProfilePic(user.getProfilePic());
      user2.setEnabled(user.isEnabled());
      user2.setEmailVerified(user.isEmailVerified());
      user2.setPhoneVerified(user.isPhoneVerified());
      user2.setProvider(user.getProvider());
      user2.setProviderUserId(user.getProviderUserId());

      User save = userRepo.save(user2);

      return Optional.ofNullable(save);

   }

   @Override
   public void deleteUser(String id) {
      User user2 = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found bu this id"));
      userRepo.delete(user2);
   }

   @Override
   public boolean isUserExist(String userId) {
      User user2 = userRepo.findById(userId).orElse(null);
      return user2 != null ? true : false;
   }

   @Override
   public boolean isUserExistByEmail(String email) {
      User user2 = userRepo.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("User not found by this email"));
      return user2 != null ? true : false;

   }

   @Override
   public List<User> getAllUser() {

      List<User> list = userRepo.findAll();

      return list;
   }

   @Override
   public User getUserByEmail(String email) {
      return userRepo.findByEmail(email).orElse(null);
   }

}
