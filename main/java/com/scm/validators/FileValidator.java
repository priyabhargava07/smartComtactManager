
package com.scm.validators;

import java.io.IOException;



import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {
    public FileValidator(){System.out.println("this method is called valid file");}
    private static final long MAX_FILE_SIZE = 1024 * 1024 * 2; // 2MB

    // type

    // height

    // width

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
  
        if (file == null || file.isEmpty()) {
             context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File cannot be empty").addConstraintViolation();
            return true;

        }

        // file size

        System.out.println("file size: " + file.getSize());

        if (file.getSize() > MAX_FILE_SIZE) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File size should be less than 2MB").addConstraintViolation();
            return false;
        }

        try {
        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());

        // If file is not an image, bufferedImage will be null
        if (bufferedImage == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid image file format").addConstraintViolation();
            return false;
        }

        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        if (width > 1920 || height > 1080) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Image resolution should not exceed 1920x1080 pixels")
                    .addConstraintViolation();
            return false;
        }
    } catch (IOException e) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("Error processing image file").addConstraintViolation();
        return false;
    }

       
        return true;
    }

}
