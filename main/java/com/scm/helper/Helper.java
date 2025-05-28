package com.scm.helper;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class Helper {
    @Value("${BASE_URL}")
    private String baseUrl;

    public static String getEmailOfLoginInUser(Authentication authentication){
        
        String userName="";
       if(authentication instanceof OAuth2AuthenticationToken){ 
        var oAuth2AuthenticationToken  = (OAuth2AuthenticationToken)authentication;
         var oauth2User =(OAuth2User)authentication.getPrincipal();
         var clientId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
         
         if(clientId.equalsIgnoreCase("google")){

            System.out.println("Geeting email from the google");
          userName = oauth2User.getAttribute("email").toString();

         }
         else if(clientId.equalsIgnoreCase("github")){
            System.out.println("Geeting emailfrom github");
            userName = oauth2User.getAttribute("email") !=null 
            ? oauth2User.getAttribute("email").toString() 
            : oauth2User.getAttribute("login").toString() + "@gmail.com";
            
         }
       

       }else{
        System.out.println("getting from database");
        userName = authentication.getName();
       }
      
       return userName;
       
    }


    public  String getLinkForEmailVerification(String emailToken){

      String link = this.baseUrl+"/auth/verify-email?token="+emailToken;
      
      return link;
    }
}
