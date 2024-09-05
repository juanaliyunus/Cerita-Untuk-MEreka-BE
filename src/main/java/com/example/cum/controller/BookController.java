package com.example.cum.controller;

import com.example.cum.dto.request.UpdateBookRequest;
import com.example.cum.dto.response.BookResponse;
import com.example.cum.dto.response.WebResponse;
import com.example.cum.service.BookService;
import com.example.cum.service.ImageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/books")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class BookController {
    private final BookService bookService;
    private final ImageService imageService;

    @GetMapping("/public/{filename}")
    public ResponseEntity<byte[]> getAvatar(@PathVariable String filename) {
        byte[] bytes = imageService.loadFileAsBytes(filename);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
    }

    @GetMapping
    public WebResponse<List<BookResponse>> getBooks(){
        List<BookResponse> response = bookService.getAll();
        return WebResponse.<List<BookResponse>>builder()
               .status(HttpStatus.OK)
               .message("success response")
               .data(response)
               .build();
    }

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<WebResponse<String>> uploadAvatar(@RequestPart("avatar") MultipartFile cover, @RequestBody UpdateBookRequest request) {
        bookService.addBookCover(request, cover);
        WebResponse<String> commonResponse = WebResponse.<String>builder()
                .message("File uploaded successfully")
                .status(HttpStatus.OK)
                .data("")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

}
