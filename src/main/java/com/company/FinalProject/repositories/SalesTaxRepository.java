package com.company.FinalProject.repositories;

import com.company.FinalProject.models.SalesTax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesTaxRepository extends JpaRepository<SalesTax, Integer> {
    SalesTax findRateByState(String state);
}
