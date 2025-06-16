package com.erdem.repository;

import com.erdem.model.StockOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStockOperationRepository extends JpaRepository<StockOperation,Long> {
}
