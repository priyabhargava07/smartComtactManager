package com.scm.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.entity.Providers;
import com.scm.entity.User;
import com.scm.helper.AppConstants;
import com.scm.repo.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenicationSuccessHandler implements AuthenticationSuccessHandler {

   Logger logger =LoggerFactory.getLogger(OAuthAuthenicationSuccessHandler.class);

  @Autowired
   private UserRepo userrepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        // TODO Auto-generated method stub

        // logger.info("OAuthAuthenicationSuccessHandler");

        var OAuth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;

        String authorizedClientRegistrationId = OAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

        // logger.info(authorizedClientRegistrationId);
        var oauthUser = (DefaultOAuth2User)authentication.getPrincipal();
        oauthUser.getAttributes().forEach((key,value)->{
          logger.info("printing the value of the oauth user");
          logger.info( "key :" + key +" " +" value :"+value);

        });
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        user.setEnabled(true);
        user.setEmailVerified(true);
        user.setPassword("password");
    
        
        if(authorizedClientRegistrationId.equalsIgnoreCase("google")){
          //  logger.info("Provider is : google");

           user.setEmail(oauthUser.getAttribute("email").toString());
           user.setProfilePic(oauthUser.getAttribute("name").toString());
           user.setName(oauthUser.getAttribute("name").toString());
           user.setProfilePic(oauthUser.getAttribute("picture").toString());
           user.setProviderUserId(oauthUser.getName());
          //  user.setProfilePic(oauthUser.getAttribute("picture").toString());
           user.setProvider(Providers.GOOGLE);
           user.setAbout("this account is created by the google");
        }
        else if(authorizedClientRegistrationId.equalsIgnoreCase("github")){
            logger.info("provider is github");
            String email = oauthUser.getAttribute("email")!=null ? oauthUser.getAttribute("email").toString() 
            : oauthUser.getAttribute("login").toString() +"@gmail.com" ;

            String picture = oauthUser.getAttribute("avatar_url").toString();
            String name = oauthUser.getAttribute("name").toString();
            String providerUserId = oauthUser.getName();

            user.setEmail(email);
            user.setProfilePic(picture);
            user.setName(name);
            user.setProvider(Providers.GITHUB);
            user.setProviderUserId(providerUserId);
            user.setAbout("this account is created by the github");
        }
        else{
          // logger.info("OauthAuthenicationSuccesHandler:Unkown provider");
        }

         // we have to identgify the which provider is and according to that  we will change the details 


        /* 

        // data database save 
        DefaultOAuth2User user=(DefaultOAuth2User)authentication.getPrincipal();
         logger.info("name"+ user.getName() );
         user.getAttributes().forEach((key,value)->{
            logger.info("{}=>{}",key,value);
         });
         logger.info(user.getAttributes().toString());

         String email = user.getAttribute("email").toString();
         String name= user.getAttribute("name").toString();
         String picture = user.getAttribute("picture").toString();

         // create the user and saving in the databse
         User user1 = new User();
         user1.setEmail(email);
         user1.setName(name);
         user1.setProfilePic(picture);
         user1.setPassword("password");
         user1.setUserId(UUID.randomUUID().toString());
         user1.setProvider(Providers.GOOGLE);
         user1.setProviderUserId(user.getName());
         user1.setRoleList(List.of(AppConstants.ROLE_USER));
         user1.setEnabled(true);
         user1.setEmailVerified(true);
         user1.setAbout("this account is creaated using the google");
     */
        User user2 = userrepo.findByEmail(user.getEmail()).orElse(null);// if the user is  present then we got the user otherwaise the null be provided
          if(user2== null){
            userrepo.save(user);
            // logger.info("USer created in the databse");
          }

        
        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }


}
