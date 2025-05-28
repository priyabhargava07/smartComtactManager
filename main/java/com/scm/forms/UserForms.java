package com.scm.forms;

import org.springframework.web.multipart.MultipartFile;

import com.scm.validators.ValidFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@ToString
public class UserForms {

@NotBlank(message="username is required")
@Size( min=3,message="Min 3 characters are required")
 private String name;
 @Email(message = "Invaild Email Address")
 @NotBlank(message = "Email is required")
 private String email;
 @NotBlank(message="password is required")
 @Size(min=6,max=12,message ="Min 6 characters and maxnium 12 characters ")
 private String password;
 @Size(min=10,max=12 , message="Invalid Phone Number")
 private String phoneNumber;
 @NotBlank(message = "you have write something it can not be blanked")
 private String about;

  @ValidFile(message = "Invalid File")
    private MultipartFile profileImage;

    private String profilePic;

}
