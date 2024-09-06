package com.example.cum.service.impl;

import com.example.cum.dto.request.DonorCreateRequest;
import com.example.cum.dto.request.DonorUpdateRequest;
import com.example.cum.dto.response.DonorResponse;
import com.example.cum.dto.response.PagebleResponse;
import com.example.cum.entity.Donor;
import com.example.cum.entity.User;
import com.example.cum.repository.DonorRepository;
import com.example.cum.repository.UserRepository;
import com.example.cum.service.DonorService;
import com.example.cum.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DonorServiceImpl implements DonorService {

    private final DonorRepository donorRepository;
    private final UserRepository userRepository;
    private final ValidationService validationService;

    @Override
    public void createDonor(User user,DonorCreateRequest request) {
        validationService.validate(request);

        Optional<Donor> donor = donorRepository.findByEmail(request.getEmail());
        if (donor.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email already exists");
        }
        var donorNew = new Donor();
        donorNew.setUser(user);
        donorNew.setEmail(request.getEmail());
        donorNew.setAddress(request.getAddress());
        donorNew.setPhoneNumber(request.getPhoneNumber());
        donorNew.setFullName(request.getFullName());
        donorNew.setCreatedAt(new Date().getTime());
        donorRepository.save(donorNew);
    }

    @Override
    public void updateDonor(User user, DonorUpdateRequest request) {
        validationService.validate(request);
        Donor donor = donorRepository.findByEmail(request.getEmail()).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Donor not found"));
        donor.setEmail(request.getEmail());
        donor.setAddress(request.getAddress());
        donor.setPhoneNumber(request.getPhoneNumber());
        donor.setFullName(request.getFullName());
        donorRepository.save(donor);
    }

    @Override
    @Transactional
    public void deleteDonor(String id) {
        Donor donor = donorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Donor not found"));
        System.out.println("test 123");
        System.out.println(id);
        System.out.println(donor.getId());
        donorRepository.deleteById(donor.getId());
    }

    @Override
    public DonorResponse getDonorByUser(String userId) {
        System.out.println(userId);
        var user = userRepository.findById(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not find"));
        Donor donor = donorRepository.findByUser(user).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Donor not found"));
        return convert(donor);
    }

    public DonorResponse getDonorByUserId(String id) {
//        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
        Donor donor = donorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Donor not found"));
        return convert(donor);
    }

    public PagebleResponse<DonorResponse> getAllDonors(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
         Page<Donor> donors = donorRepository.findAll(pageable);
        return PagebleResponse.<DonorResponse>builder()
                .total_page(donors.getTotalPages())
                .page(page)
                .limit(limit)
                .data(donors.stream().map(this::convert).toList())
                .build();
    }

    private DonorResponse convert(Donor donor) {
        return DonorResponse.builder()
               .id(donor.getId())
               .email(donor.getEmail())
               .address(donor.getAddress())
               .phoneNumber(donor.getPhoneNumber())
               .fullname(donor.getFullName())
                .avatar(donor.getAvatar())
               .build();
    }
}
