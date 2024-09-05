package com.example.cum.service.impl;

import com.example.cum.dto.request.OrphanagesNeedRequest;
import com.example.cum.dto.request.UpdateOrphanagesNeedRequest;
import com.example.cum.dto.response.OrphanagesNeedResponse;
import com.example.cum.dto.response.PagebleResponse;
import com.example.cum.entity.OrphanagesNeed;
import com.example.cum.repository.OrphanagesNeedRepository;
import com.example.cum.repository.OrphanagesRepository;
import com.example.cum.repository.UserRepository;
import com.example.cum.service.OrphanagesNeedService;
import com.example.cum.service.OrphanagesService;
import com.example.cum.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrphanagesNeedServiceImpl implements OrphanagesNeedService {
    private final OrphanagesNeedRepository orphanagesNeedRepository;
    private final ValidationService validationService;
    private final OrphanagesRepository orphanagesRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void addOrphanagesNeed(OrphanagesNeedRequest request) {

        validationService.validate(request);

        var user = userRepository.findById(request.getUserId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"not found") );

        var orphanages = orphanagesRepository.findByUser(user).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No orphanages found"));

        orphanagesNeedRepository.save(OrphanagesNeed.builder()
                .bookName(request.getBookName().toLowerCase())
                .quantity(request.getQuantity())
                .orphanages(orphanages)
                .status("requested")
                .createdAt(System.currentTimeMillis())
                .targetQuantity(0).build());
    }

    @Override
    @Transactional
    public void deleteOrphanagesNeed(String id) {
        var need = orphanagesNeedRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No orphanages need found"));
        need.setStatus("cancelled");
        need.setUpdatedAt(System.currentTimeMillis());
        orphanagesNeedRepository.save(need);
    }

    @Override
    @Transactional
    public void updateOrphanagesNeed(String id, UpdateOrphanagesNeedRequest request) {
        validationService.validate(request);
        var need = orphanagesNeedRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No orphanages need found"));
        need.setBookName(request.getBookName());
        need.setQuantity(request.getQuantity());
        need.setUpdatedAt(System.currentTimeMillis());
        orphanagesNeedRepository.save(need);
    }

    @Override
    public void updateStatus(String id, String status) {
        //Requested Fulfilled Cancelled
        var statusLower = status.toLowerCase();
        if (!statusLower.equals("requested") &&!statusLower.equals("fulfilled") &&!statusLower.equals("cancelled")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status");
        }
        var need = orphanagesNeedRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No orphanages need found"));
        need.setStatus(status);
        need.setUpdatedAt(System.currentTimeMillis());
        orphanagesNeedRepository.save(need);
    }

    @Override
    public PagebleResponse<OrphanagesNeedResponse> getOrphanagesNeedStatus(int page, int limit) {
        var needs = orphanagesNeedRepository.findAllByStatus("requested", PageRequest.of(page, limit));
        return PagebleResponse.<OrphanagesNeedResponse>builder()
                .total_page(needs.getTotalPages())
                .page(page)
                .limit(limit)
               .data(needs.stream().map(this::convert).toList())
               .build();
    }
    @Override
    public PagebleResponse<OrphanagesNeedResponse> getOrphanagesNeed(int page, int limit) {
        var needs = orphanagesNeedRepository.findAll(PageRequest.of(page, limit));
        return PagebleResponse.<OrphanagesNeedResponse>builder()
                .total_page(needs.getTotalPages())
                .page(page)
                .limit(limit)
                .data(needs.stream().map(this::convert).toList())
                .build();
    }

    public List<OrphanagesNeedResponse> getOrphanagesNeedById(String orphanageId) {
        var needs = orphanagesNeedRepository.getByStatusAndOrphanages("requested",orphanageId);
        var responses = new ArrayList<OrphanagesNeedResponse>();
        for (OrphanagesNeed needsResponse : needs) {
            responses.add(convert(needsResponse));
        }
        return responses;
    }

    private OrphanagesNeedResponse convert(OrphanagesNeed request) {
        return OrphanagesNeedResponse.builder()
               .id(request.getId())
               .bookName(request.getBookName())
               .quantity(request.getQuantity())
               .orphanagesId(request.getOrphanages().getId())
               .status(request.getStatus())
                .createdAt(request.getCreatedAt())
                .targetQuantity(request.getTargetQuantity())
               .build();
    }
}
