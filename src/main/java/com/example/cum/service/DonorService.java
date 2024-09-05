package com.example.cum.service;

import com.example.cum.dto.request.DonorCreateRequest;
import com.example.cum.dto.request.DonorUpdateRequest;
import com.example.cum.dto.response.DonorResponse;
import com.example.cum.dto.response.PagebleResponse;
import com.example.cum.entity.User;

public interface DonorService {
    void createDonor(User user,DonorCreateRequest request);
    void updateDonor(User user ,DonorUpdateRequest request);
    void deleteDonor(String id);
    DonorResponse getDonorByUser(String userId);
    PagebleResponse<DonorResponse> getAllDonors(int page, int limit); //for admin
    DonorResponse getDonorByUserId(String id); //for user
}
