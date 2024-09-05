package com.example.cum.service;

import com.example.cum.dto.request.OrphanagesNeedRequest;
import com.example.cum.dto.request.UpdateOrphanagesNeedRequest;
import com.example.cum.dto.response.OrphanagesNeedResponse;
import com.example.cum.dto.response.PagebleResponse;
import com.example.cum.entity.Orphanages;

import java.util.List;

public interface OrphanagesNeedService {
    void addOrphanagesNeed(OrphanagesNeedRequest request);
    void deleteOrphanagesNeed(String id);
    void updateOrphanagesNeed(String id, UpdateOrphanagesNeedRequest request);
    void updateStatus(String id, String status);
    PagebleResponse<OrphanagesNeedResponse> getOrphanagesNeed(int page, int limit);
    PagebleResponse<OrphanagesNeedResponse> getOrphanagesNeedStatus(int page, int limit);
    List<OrphanagesNeedResponse> getOrphanagesNeedById(String orphanageId);
}
