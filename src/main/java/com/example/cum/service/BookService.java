package com.example.cum.service;

import com.example.cum.dto.request.UpdateBookRequest;
import com.example.cum.dto.response.BookResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {
    void updateBookCover(String id);
    void deleteBook(String id);
//    add cover

    void addBookCover(UpdateBookRequest request, MultipartFile file);
    List <BookResponse> getAll();
}
