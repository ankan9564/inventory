package com.example.inventory.repository;

import com.example.inventory.model.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StockHistoryRepository extends JpaRepository<StockHistory, Long> {

    @Query("""
           SELECT COALESCE(SUM(sh.quantityChange * -1) / COUNT(DISTINCT DATE(sh.timestamp)), 0)
           FROM StockHistory sh
           WHERE sh.sku = :sku AND sh.type = 'OUT'
           """)
    Integer findAverageDailyUsage(String sku);

}
