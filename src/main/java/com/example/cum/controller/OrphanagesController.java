package com.example.cum.controller;

import com.example.cum.dto.request.OrphanagesUpdateRequest;
import com.example.cum.dto.response.OrphanagesResponse;
import com.example.cum.dto.response.PagebleResponse;
import com.example.cum.dto.response.WebResponse;
import com.example.cum.entity.User;
import com.example.cum.service.OrphanagesService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/orphanages")
@SecurityRequirement(name = "Authorization")
public class OrphanagesController {
    private final OrphanagesService orphanagesService;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<PagebleResponse> getDonors(@RequestParam(defaultValue = "0") int page) {
        var orphanages = orphanagesService.getAll(page,15);
        return WebResponse.<PagebleResponse>builder()
                .status(HttpStatus.OK)
                .message("Get donors successfully")
                .data(orphanages)
                .build();
    }

    @GetMapping(
            path = "/by-status",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<PagebleResponse> getDonorsStatus(@RequestParam(defaultValue = "0") int page) {
        var orphanages = orphanagesService.getAllByStatus(page,15);
        return WebResponse.<PagebleResponse>builder()
                .status(HttpStatus.OK)
                .message("Get donors successfully")
                .data(orphanages)
                .build();
    }
    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<OrphanagesResponse> getDonor(@PathVariable String id) {
        var orphanages = orphanagesService.getOrphanagesById(id);
        return WebResponse.<OrphanagesResponse>builder()
               .status(HttpStatus.OK)
               .message("Get donor successfully")
               .data(orphanages)
               .build();
    }
    @GetMapping(
            path = "/by-user/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<OrphanagesResponse> getDonorByUsre(User user, @PathVariable  String userId) {
        var orphanages = orphanagesService.getOrphanagesByUser(userId);
        return WebResponse.<OrphanagesResponse>builder()
                .status(HttpStatus.OK)
                .message("Get donor successfully")
                .data(orphanages)
                .build();
    }
    @PutMapping(
            path = "/{id}/status",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> updateStatus(@PathVariable String id, @RequestBody String request) {
        String result = request.replace("\"","");
        orphanagesService.updateOrphanagesStatus(id,result);
        return WebResponse.<String>builder()
                .status(HttpStatus.OK)
                .message("Update orphanages status successfully")
                .data("")
                .build();
    }

    @PutMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> update(@PathVariable String id, @RequestBody OrphanagesUpdateRequest request) {
        orphanagesService.updateOrphanages(id, request);
        return WebResponse.<String>builder()
                .status(HttpStatus.OK)
                .message("Update orphanages status successfully")
                .data("")
                .build();
    }

    @DeleteMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(@PathVariable String id) {
        orphanagesService.deleteOrphanages(id);
        return WebResponse.<String>builder()
               .status(HttpStatus.OK)
               .message("Delete orphanages successfully")
                .data("")
               .build();
    }
}

