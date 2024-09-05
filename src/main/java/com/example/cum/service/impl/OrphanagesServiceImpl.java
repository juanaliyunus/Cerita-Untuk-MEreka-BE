package com.example.cum.service.impl;

import com.example.cum.dto.request.OrphanagesCreateRequest;
import com.example.cum.dto.request.OrphanagesUpdateRequest;
import com.example.cum.dto.response.OrphanagesResponse;
import com.example.cum.dto.response.PagebleResponse;
import com.example.cum.entity.Orphanages;
import com.example.cum.entity.User;
import com.example.cum.repository.OrphanagesRepository;
import com.example.cum.repository.UserRepository;
import com.example.cum.service.OrphanagesService;
import com.example.cum.service.UserService;
import com.example.cum.service.ValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrphanagesServiceImpl implements OrphanagesService {
    private final OrphanagesRepository orphanagesRepository;
    private final UserRepository userRepository;
    private final ValidationService validationService;
    public void createOrphanages(User user, OrphanagesCreateRequest request) {
        validationService.validate(request);
        Optional<Orphanages> orphanages =  orphanagesRepository.findByUser(user);
        if (orphanages.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Orphanages already exists");
        }
        Orphanages orphanagesNew = new Orphanages();
        orphanagesNew.setUser(user);
        orphanagesNew.setName(request.getName());
        orphanagesNew.setContactEmail(request.getEmail());
        orphanagesNew.setAddress(request.getAddress());
        orphanagesNew.setContactPhone(request.getPhoneNumber());
        orphanagesNew.setDescription(request.getDescription());
        orphanagesNew.setWebUrl(request.getWebUrl());
        orphanagesNew.setStatus("pending");
        orphanagesNew.setCreatedAt(System.currentTimeMillis());

        orphanagesRepository.save(orphanagesNew);

    }

    public void updateOrphanages(String id, OrphanagesUpdateRequest request) {
        validationService.validate(request);
        log.info(request.getName() + " updated orphan");
        Orphanages orphanages =  orphanagesRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No found orphanage"));
        orphanages.setName(request.getName());
        orphanages.setAddress(request.getAddress());
        orphanages.setContactEmail(request.getEmail());
        orphanages.setContactPhone(request.getPhoneNumber());
        orphanages.setDescription(request.getDescription());
        orphanages.setWebUrl(request.getWebUrl());
        orphanagesRepository.save(orphanages);
    }

    public void deleteOrphanages(String id) {
        Orphanages orphanages =  orphanagesRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No found orphanage"));
        orphanagesRepository.delete(orphanages);
        var users = userRepository.findById(orphanages.getUser().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No found orphanage"));
        userRepository.delete(users);
    }

    public OrphanagesResponse getOrphanagesById(String id) {
        Orphanages orphanages =  orphanagesRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No found orphanage"));
        return convert(orphanages);
    }

    public OrphanagesResponse getOrphanagesByUser(String userId) {
        var user =  userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No found orphanage"));
        Orphanages orphanages =  orphanagesRepository.findByUser(user).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No found orphanage"));
        return convert(orphanages);
    }

    @Override
    public PagebleResponse<OrphanagesResponse> getAllByStatus(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
//        ambil approved
        Page<Orphanages> orphanages = orphanagesRepository.findAllByStatus("approved",pageable);
        return PagebleResponse.<OrphanagesResponse>builder()
                .total_page(orphanages.getTotalPages())
                .page(page)
                .limit(limit)
                .data(orphanages.stream().map(this::convert).toList())
                .build();
    }

    @Override
    @Transactional
    public PagebleResponse<OrphanagesResponse> getAll(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Orphanages> orphanages = orphanagesRepository.findAll(pageable);
        return PagebleResponse.<OrphanagesResponse>builder()
                .total_page(orphanages.getTotalPages())
                .page(page)
                .limit(limit)
                .data(orphanages.stream().map(this::convert).toList())
                .build();
    }

    @Override
    @Transactional
    public void updateOrphanagesStatus(String id, String request) {
        validationService.validate(request);
        Orphanages orphanages =  orphanagesRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No found orphanage"));
        orphanages.setStatus(request.toLowerCase());
        orphanagesRepository.save(orphanages);
    }

    private OrphanagesResponse convert(Orphanages orphanages){
        return OrphanagesResponse.builder()
               .id(orphanages.getId())
               .name(orphanages.getName())
               .address(orphanages.getAddress())
               .email(orphanages.getContactEmail())
               .phoneNumber(orphanages.getContactPhone())
               .description(orphanages.getDescription())
               .webUrl(orphanages.getWebUrl())
                .avatar(orphanages.getAvatar())
                .status(orphanages.getStatus())
               .build();
    }


}
