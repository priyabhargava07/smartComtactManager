package com.scm;

import com.scm.entity.User;
import com.scm.helper.AppConstants;
import com.scm.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class SmartContactManagerApplication implements  CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SmartContactManagerApplication.class, args);
	}

		@Autowired
		private UserRepo userRepo;
	    @Autowired
	    private PasswordEncoder passwordEncoder;
		@Override
	   public void run(String... args){
			User user = new User();
			user.setUserId(UUID.randomUUID().toString());
			user.setName("Admin");
			user.setEmail("admin@gmail.com");
			user.setPassword(passwordEncoder.encode("admin1234"));
			user.setRoleList(List.of(AppConstants.ROLE_USER));
			user.setEmailVerified(true);
			user.setAbout("this is the admin created for testing purpose only");
			user.setPhoneVerified(true);
			user.setPhoneNumber("3478908765");
			user.setEnabled(true);
			user.setProfilePic("https://cdn.prod.website-files.com/62d84e447b4f9e7263d31e94/6399a4d27711a5ad2c9bf5cd_ben-sweet-2LowviVHZ-E-unsplash-1.jpeg");
			userRepo.findByEmail("admin@gmail.com").ifPresentOrElse(user1 -> {},()->{
				userRepo.save(user);
				System.out.printf("user created");
			});


		}


}
