package com.company.FinalProject.controllers;

import com.company.FinalProject.models.Tshirt;
import com.company.FinalProject.repositories.TshirtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class TshirtController
{
    @Autowired
    TshirtRepository tshirtRepository;

    // Get All Tshirts
    @GetMapping("/tshirts")
    public List<Tshirt> getAllTshirts()
    {
        return tshirtRepository.findAll();
    }

    // Get Tshirt by tshirt id
    @GetMapping("/tshirts/{t_shirt_id}")
    public Tshirt findTshirtByID(@Valid @PathVariable int t_shirt_id)
    {
        Optional<Tshirt> returnVal = tshirtRepository.findById(t_shirt_id);
        if(returnVal.isPresent())
        {
            return returnVal.get();
        }
        else
        {   throw new IllegalArgumentException("Invalid T-shirt ID");
            //return null;
        }
    }

    //Tshirt by size
    @GetMapping("/tshirts/size/{size}")
    public List<Tshirt> getTshirtBySize(@Valid @PathVariable String size)
    {
        List<Tshirt> returnVal = tshirtRepository.findTshirtBySize(size);
        if(returnVal.size() > 0)
        {
            return returnVal;
        }
        else
        {
            throw new IllegalArgumentException("Must enter a valid T-shirt size.");
            //return null;
        }
    }

    // Get Tshirt by color
    @GetMapping("/tshirts/color/{color}")
    public List<Tshirt> getTshirtByColor(@Valid @PathVariable String color)
    {
        List<Tshirt> returnVal = tshirtRepository.findTshirtByColor(color);
        if(returnVal.size() > 0)
        {
            return returnVal;
        }
        else
        {
            throw new IllegalArgumentException("Must enter a valid T-shirt color.");
            //return null;
        }
    }

    // Add Tshirt
    @PostMapping("/tshirts")
    @ResponseStatus(HttpStatus.CREATED)
    public Tshirt addTshirt(@Valid @RequestBody Tshirt tshirt)
    {
        return tshirtRepository.save(tshirt);
    }

    // Update Tshirt
    @PutMapping("/tshirts")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTshirt(@Valid @RequestBody Tshirt tshirt)
    {
        tshirtRepository.save(tshirt);
    }

    // Delete Tshirt
    @DeleteMapping("/tshirts/{}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTshirt(@Valid @PathVariable int ID)
    {
        tshirtRepository.deleteById(ID);
    }
}


