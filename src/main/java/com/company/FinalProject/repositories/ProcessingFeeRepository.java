package com.company.FinalProject.repositories;

import com.company.FinalProject.models.ProcessingFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ProcessingFeeRepository extends JpaRepository<ProcessingFee, Integer> {
    BigDecimal findFeeByProductType(String productType);
}
