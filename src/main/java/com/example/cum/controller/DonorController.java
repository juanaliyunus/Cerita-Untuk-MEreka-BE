package com.example.cum.controller;

import com.example.cum.dto.request.DonorUpdateRequest;
import com.example.cum.dto.response.DonorResponse;
import com.example.cum.dto.response.PagebleResponse;
import com.example.cum.dto.response.WebResponse;
import com.example.cum.entity.User;
import com.example.cum.service.DonorService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/donors")
@SecurityRequirement(name = "Authorization")
public class DonorController {
    private final DonorService donorService;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<PagebleResponse> getDonors(@RequestParam(defaultValue = "0") int page) {
        var donors = donorService.getAllDonors(page,15);
        return WebResponse.<PagebleResponse>builder()
                .status(HttpStatus.OK)
                .message("Get donors successfully")
                .data(donors)
                .build();
    }
    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<DonorResponse> getDonor(@PathVariable String id) {
        var donor = donorService.getDonorByUserId(id);
        return WebResponse.<DonorResponse>builder()
               .status(HttpStatus.OK)
               .message("Get donor successfully")
               .data(donor)
               .build();
    }

    @GetMapping(
            path = "/user/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<DonorResponse> getDonorByUser(@PathVariable String id) {
        var donor = donorService.getDonorByUser(id);
        return WebResponse.<DonorResponse>builder()
               .status(HttpStatus.OK)
               .message("Get donor successfully")
               .data(donor)
               .build();
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<DonorResponse> updateDonor(User user, @RequestBody DonorUpdateRequest request) {
        donorService.updateDonor(user,request);
        return WebResponse.<DonorResponse>builder()
               .status(HttpStatus.OK)
               .message("Update donor successfully")
               .build();
    }

    @DeleteMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> deleteDonor(User user, @PathVariable String id) {
        donorService.deleteDonor(id);
        return WebResponse.<String>builder()
               .status(HttpStatus.OK)
               .message("Delete donor successfully")
               .build();
    }

}
