package com.example.cum.service.impl;

import com.example.cum.entity.Donor;
import com.example.cum.entity.Orphanages;
import com.example.cum.entity.User;
import com.example.cum.repository.DonorRepository;
import com.example.cum.repository.OrphanagesRepository;
import com.example.cum.repository.UserRepository;
import com.example.cum.service.ImageService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class ImageServiceImpl implements ImageService {
    private final Path fileStorageLocation;

    private final OrphanagesRepository orphanagesRepository;
    private final DonorRepository donorRepository;
    @Autowired
    public ImageServiceImpl(OrphanagesRepository orphanagesRepository, DonorRepository donorRepository) {

        this.orphanagesRepository = orphanagesRepository;
        this.donorRepository = donorRepository;
        this.fileStorageLocation = Path.of("assets/images/");
        try {
            Files.createDirectories(fileStorageLocation);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public String storeFile(MultipartFile file, User user) {
//        System.out.println(user);
        System.out.println(user.getRole());

        String fileName = Objects.requireNonNull(file.getOriginalFilename());
        String idFileName = user.getId() + "_" + fileName;
        if (user.getRole().equals("ROLE_ORPHANAGES")){
            Orphanages orphanages = orphanagesRepository.findByUser(user).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Orphanages not found"));
            orphanages.setAvatar(idFileName);
            orphanagesRepository.save(orphanages);
        }else {
            Donor donor = donorRepository.findByUser(user).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "donor not found"));
            donor.setAvatar(idFileName);
            donorRepository.save(donor);
        }
        try {
            Path targetLocation = fileStorageLocation.resolve(idFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return idFileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] loadFileAsBytes(String fileName) {
        Path filePath = fileStorageLocation.resolve(fileName);
        System.out.println(filePath);
        if (!Files.exists(filePath)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404),fileName+" not found");
        }
        try {
                        return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(500),e.getMessage()+" not found");
        }
    }
}
