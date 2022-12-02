package com.company.FinalProject.repositories;

import com.company.FinalProject.models.Game;
import com.company.FinalProject.models.SalesTax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SalesTaxRepository extends JpaRepository<SalesTax, Integer> {
    BigDecimal findRateByState(String state);
}
