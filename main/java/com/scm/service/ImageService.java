package com.scm.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String uploadImage(MultipartFile contactImage,String filename);

    String getUrlFromPublic(String publicId);

}

