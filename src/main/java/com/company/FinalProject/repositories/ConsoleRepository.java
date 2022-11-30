package com.company.FinalProject.repositories;

import com.company.FinalProject.models.Console;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsoleRepository extends JpaRepository<Console, Integer> {
    List<Console> findAllConsolesByManufacturer(String manufacturer);
}
