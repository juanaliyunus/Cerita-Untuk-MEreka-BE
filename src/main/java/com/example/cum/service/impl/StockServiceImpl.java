package com.example.cum.service.impl;

import com.example.cum.dto.request.CreateStockRequest;
import com.example.cum.dto.request.UpdateStockRequest;
import com.example.cum.dto.response.PagebleResponse;
import com.example.cum.dto.response.StockResponse;
import com.example.cum.entity.Orphanages;
import com.example.cum.entity.Stock;
import com.example.cum.repository.BookRepository;
import com.example.cum.repository.OrphanagesRepository;
import com.example.cum.repository.StockRepository;
import com.example.cum.service.StockService;
import com.example.cum.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;
    private final BookRepository bookRepository;
    private final ValidationService validationService;
    private final OrphanagesRepository orphanagesRepository;


    @Override
    @Transactional
    public void updateStock(UpdateStockRequest request) {
        validationService.validate(request);
        // Update the stock in the database
        Orphanages orphanages = orphanagesRepository.findById(request.getOrphanageId())
               .orElseThrow(() -> new RuntimeException("Orphanage not found"));
        Stock stock = stockRepository.findByBookNameAndOrphanages(request.getBookName(),orphanages)
               .orElseThrow(() -> new RuntimeException("Stock not found"));
        var stockNew =  stock.getQuantity() + request.getQuantity();
        stock.setQuantity( stockNew);
        stock.setUpdatedAt(System.currentTimeMillis());
        stockRepository.save(stock);
    }

    @Override
    public StockResponse getStock(String orphanages_id,String bookName) {
        var orphanages = orphanagesRepository.findById(orphanages_id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"No such orphanage"));
        // Retrieve the stock from the database
        Stock stock = stockRepository.findByBookNameAndOrphanages(bookName, orphanages)
               .orElseThrow(() -> new RuntimeException("Stock not found"));
        return convert(stock);
    }

    @Override
    public void deleteStock(String bookName) {
        // Delete the stock from the database
    }

    @Override
    @Transactional
    public void createStock(CreateStockRequest request) {
        validationService.validate(request);
        // Create the stock in the database
        Orphanages orphanages = orphanagesRepository.findById(request.getOrphanageId())
               .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Orphanage not found"));
        Stock stock = Stock.builder()
               .bookName(request.getBookName())
               .quantity(request.getQuantity())
               .orphanages(orphanages)
                .createdAt(System.currentTimeMillis())
               .build();
        stockRepository.save(stock);
    }

    @Override
    public PagebleResponse<StockResponse> getStockByOrphanages(String orphanageId,int page,int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        // Retrieve the stocks from the database
        Orphanages orphanages = orphanagesRepository.findById(orphanageId)
               .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Orphanage not found"));
        Page<Stock> stocks = stockRepository.findAllByOrphanages(orphanages, pageable);
        return PagebleResponse.<StockResponse>builder()
               .total_page(stocks.getTotalPages())
               .page(page)
               .limit(limit)
               .data(stocks.stream().map(this::convert).toList())
               .build();
    }

    @Override
    public PagebleResponse<StockResponse> getAllStock(int page,int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        // Retrieve all the stocks from the database
        Page<Stock> stocks = stockRepository.findAll(pageable);
        return PagebleResponse.<StockResponse>builder()
               .total_page(stocks.getTotalPages())
               .page(page)
               .limit(limit)
               .data(stocks.stream().map(this::convert).toList())
               .build();
    }

    private StockResponse convert (Stock stock) {
        return StockResponse.builder()
                .id(stock.getId())
                .orphanagesId(stock.getOrphanages().getId())
                .createdAt(stock.getCreatedAt())
               .bookName(stock.getBookName())
               .quantityAvailable(stock.getQuantity())
               .build();
    }
}
