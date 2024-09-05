package com.example.cum.service;

import com.example.cum.dto.request.CreateStockRequest;
import com.example.cum.dto.request.UpdateStockRequest;
import com.example.cum.dto.response.PagebleResponse;
import com.example.cum.dto.response.StockResponse;
import com.example.cum.entity.Orphanages;

public interface StockService {
    void updateStock(UpdateStockRequest request);
    StockResponse getStock(String orphanages_id,String bookName);
    void deleteStock(String bookName);
    void createStock(CreateStockRequest request);
    PagebleResponse<StockResponse> getStockByOrphanages(String orphanageId,int page,int limit);
    PagebleResponse<StockResponse> getAllStock(int page, int limit);
}
