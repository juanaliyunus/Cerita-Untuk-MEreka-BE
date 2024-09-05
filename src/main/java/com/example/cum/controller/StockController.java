package com.example.cum.controller;


import com.example.cum.dto.request.CreateStockRequest;
import com.example.cum.dto.request.UpdateStockRequest;
import com.example.cum.dto.response.PagebleResponse;
import com.example.cum.dto.response.StockResponse;
import com.example.cum.dto.response.WebResponse;
import com.example.cum.service.StockService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/stocks")
@SecurityRequirement(name = "Authorization")
public class StockController {

    private final StockService stockService;
    @PutMapping(
            consumes = "application/json",
            produces = "application/json"
    )
    public WebResponse<String> update(@RequestBody UpdateStockRequest request){
        stockService.updateStock(request);
        return WebResponse.<String>builder()
               .status(HttpStatus.OK)
               .message("Stock updated successfully")
               .data("OK")
               .build();
    }

    @GetMapping(
            path = "/{orphanageId}/{bookName}",
            produces = "application/json"
    )
    public WebResponse<StockResponse> getStock(@PathVariable String orphanageId, @PathVariable String bookName){
        var response = stockService.getStock(orphanageId, bookName);
        return WebResponse.<StockResponse>builder()
               .status(HttpStatus.OK)
               .message("Stock retrieved successfully")
               .data(response)
               .build();
    }

    @PostMapping(
            consumes = "application/json",
            produces = "application/json"
    )
    public WebResponse<String> create(@RequestBody CreateStockRequest request){
        stockService.createStock(request);
        return WebResponse.<String>builder()
               .status(HttpStatus.OK)
               .message("Stock created successfully")
               .data("OK")
               .build();
    }

    @GetMapping(
            produces = "application/json"
    )
    public WebResponse<PagebleResponse<StockResponse>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "15") int limit){
        var response = stockService.getAllStock(page, limit);
        return WebResponse.<PagebleResponse<StockResponse>>builder()
               .status(HttpStatus.OK)
               .message("Stock retrieved successfully")
               .data(response)
               .build();
    }

    @GetMapping(
            path = "/orphanages/{orphanageId}",
            produces = "application/json"
    )
    public WebResponse<PagebleResponse<StockResponse>> getByOrphanage(@PathVariable String orphanageId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "15") int limit){
        var response = stockService.getStockByOrphanages(orphanageId, page, limit);
        return WebResponse.<PagebleResponse<StockResponse>>builder()
               .status(HttpStatus.OK)
               .message("Stock retrieved successfully")
               .data(response)
               .build();
    }
}
