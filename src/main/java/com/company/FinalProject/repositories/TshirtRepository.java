package com.company.FinalProject.repositories;

import com.company.FinalProject.models.Tshirt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TshirtRepository extends JpaRepository<Tshirt, Integer>
{
    List<Tshirt> findTshirtByColor(String color);
    List<Tshirt> findTshirtBySize(String size);
}
