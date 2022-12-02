package com.company.FinalProject.repositories;

import com.company.FinalProject.models.ProcessingFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessingFeeRepository extends JpaRepository<ProcessingFee, Integer> {
    ProcessingFee findFeeByProductType(String productType);
}
