package com.example.cum.controller;

import com.example.cum.dto.response.UploadResponse;
import com.example.cum.dto.response.WebResponse;
import com.example.cum.entity.User;
import com.example.cum.service.ImageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path = "/api/v1/avatars")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class ImageController {
    private final ImageService imageService;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<WebResponse<UploadResponse>> uploadAvatar(@RequestPart("avatar") MultipartFile avatar, User user) {
        String fileName = imageService.storeFile(avatar, user);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/avatars/public/")
                .path(fileName)
                .toUriString();

        UploadResponse avatarResponse = UploadResponse.builder()
                .url(fileDownloadUri)
                .fileName(fileName)
                .build();

        WebResponse<UploadResponse> commonResponse = WebResponse.<UploadResponse>builder()
                .message("File uploaded successfully")
                .status(HttpStatus.OK)
                .data(avatarResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @GetMapping("/public/{filename}")
    public ResponseEntity<byte[]> getAvatar(@PathVariable String filename) {
        byte[] bytes = imageService.loadFileAsBytes(filename);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
    }
}
