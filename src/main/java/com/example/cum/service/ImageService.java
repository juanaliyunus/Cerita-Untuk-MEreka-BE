package com.example.cum.service;

import com.example.cum.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String storeFile(MultipartFile file, User user);
    byte[] loadFileAsBytes(String fileName);
}
