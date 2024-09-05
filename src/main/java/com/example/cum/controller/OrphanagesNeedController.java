package com.example.cum.controller;


import com.example.cum.dto.request.OrphanagesNeedRequest;
import com.example.cum.dto.request.UpdateOrphanagesNeedRequest;
import com.example.cum.dto.response.OrphanagesNeedResponse;
import com.example.cum.dto.response.PagebleResponse;
import com.example.cum.dto.response.WebResponse;
import com.example.cum.service.OrphanagesNeedService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/orphanages/needs")
@SecurityRequirement(name = "Authorization")
public class OrphanagesNeedController {

    private final OrphanagesNeedService orphanagesNeedService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> addRequest(@RequestBody OrphanagesNeedRequest request){
        log.info("Adding orphanages need request");
        orphanagesNeedService.addOrphanagesNeed(request);
        return WebResponse.<String>builder()
               .status(HttpStatus.CREATED)
               .message("Orphanages need request created successfully")
               .build();
    }

    @DeleteMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> deleteRequest(@PathVariable String id){
        log.info("Deleting orphanages need request for id {}", id);
        orphanagesNeedService.deleteOrphanagesNeed(id);
        return WebResponse.<String>builder()
               .status(HttpStatus.OK)
               .message("Orphanages need request deleted successfully")
               .build();
    }

    @PutMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> updateRequest(@PathVariable String id, @RequestBody UpdateOrphanagesNeedRequest request){
        log.info("Updating orphanages need request for id {}", id);
        orphanagesNeedService.updateOrphanagesNeed(id, request);
        return WebResponse.<String>builder()
               .status(HttpStatus.OK)
               .message("Orphanages need request updated successfully")
               .build();
    }

    @PutMapping(
            path = "/{id}/status",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> updateStatus(@PathVariable String id, @RequestBody String status){
        log.info("Updating orphanages need status for id {}", id);
        String result = status.replace("\"", "");
        orphanagesNeedService.updateStatus(id, result);
        return WebResponse.<String>builder()
               .status(HttpStatus.OK)
               .message("Orphanages need status updated successfully")
               .build();
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<PagebleResponse<OrphanagesNeedResponse>> getRequests(@RequestParam(defaultValue = "0") int page){
        log.info("Getting all orphanages need requests");
        var requests = orphanagesNeedService.getOrphanagesNeed(page, 15);
        return WebResponse.<PagebleResponse<OrphanagesNeedResponse>>builder()
               .status(HttpStatus.OK)
               .message("Orphanages need requests retrieved successfully")
               .data(requests)
               .build();
    }

    @GetMapping(
            path = "/status",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<PagebleResponse<OrphanagesNeedResponse>> getRequestsByStatus(@RequestParam(defaultValue = "0") int page){
        log.info("Getting all orphanages need requests by status");
        var requests = orphanagesNeedService.getOrphanagesNeedStatus(page, 15);
        return WebResponse.<PagebleResponse<OrphanagesNeedResponse>>builder()
               .status(HttpStatus.OK)
               .message("Orphanages need requests retrieved successfully")
               .data(requests)
               .build();
    }

    @GetMapping(
            path = "/by/{orphanageId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<OrphanagesNeedResponse>> getRequestsById(@PathVariable String orphanageId){
        var requests = orphanagesNeedService.getOrphanagesNeedById(orphanageId);
        return WebResponse.<List<OrphanagesNeedResponse>>builder()
                .status(HttpStatus.OK)
                .message("Orphanages need requests retrieved successfully")
                .data(requests)
                .build();
    }
}
