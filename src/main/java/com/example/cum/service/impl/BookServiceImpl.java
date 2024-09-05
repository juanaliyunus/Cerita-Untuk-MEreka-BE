package com.example.cum.service.impl;

import com.example.cum.dto.request.UpdateBookRequest;
import com.example.cum.dto.response.BookResponse;
import com.example.cum.entity.Book;
import com.example.cum.entity.Donor;
import com.example.cum.entity.Orphanages;
import com.example.cum.repository.BookRepository;
import com.example.cum.repository.DonorRepository;
import com.example.cum.repository.OrphanagesRepository;
import com.example.cum.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    private final Path fileStorageLocation;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {

        this.bookRepository = bookRepository;

        this.fileStorageLocation = Path.of("assets/images/");
        try {
            Files.createDirectories(fileStorageLocation);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateBookCover(String id) {

    }

    @Override
    public void deleteBook(String id) {

    }

    @Override
    @Transactional
    public void addBookCover(UpdateBookRequest request, MultipartFile file) {
        String fileName = Objects.requireNonNull(file.getOriginalFilename());
        String idFileName = request.getId() + "_" + fileName;
        Book book = bookRepository.findById(request.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
        book.setCover(idFileName);
        book.setYear(request.getYear());
        book.setGenre(request.getGenre());
        bookRepository.save(book);
        try {
            Path targetLocation = fileStorageLocation.resolve(idFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
//            return idFileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<BookResponse> getAll() {
        var book = bookRepository.findAll();
        var fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/books/public/");
        return book.stream().peek((b) -> {
            b.setCover(fileDownloadUri.path(b.getCover()).toUriString());
        }).map(this::convert).toList();
    }

    private BookResponse convert(Book book){
        return BookResponse.builder()
               .id(book.getId())
               .name(book.getName())
                .genre(book.getGenre())
                .year(book.getYear())
                .coverUrl(book.getCover())
               .build();
    }
}
