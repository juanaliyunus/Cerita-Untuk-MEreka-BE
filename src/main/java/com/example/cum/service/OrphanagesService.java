package com.example.cum.service;

import com.example.cum.dto.request.OrphanagesCreateRequest;
import com.example.cum.dto.request.OrphanagesUpdateRequest;
import com.example.cum.dto.response.DonorResponse;
import com.example.cum.dto.response.OrphanagesResponse;
import com.example.cum.dto.response.PagebleResponse;
import com.example.cum.entity.User;

public interface OrphanagesService {
    void createOrphanages(User user, OrphanagesCreateRequest request);
    void updateOrphanages(String id, OrphanagesUpdateRequest request);
    void deleteOrphanages(String id);
    OrphanagesResponse getOrphanagesById(String id);
    OrphanagesResponse getOrphanagesByUser(String userId);
    PagebleResponse<OrphanagesResponse> getAllByStatus(int page, int limit);

//    admin
    PagebleResponse<OrphanagesResponse> getAll(int page, int limit);
    void updateOrphanagesStatus(String id, String request);
}
