package com.company.FinalProject.controllers;

import com.company.FinalProject.models.Console;
import com.company.FinalProject.repositories.ConsoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ConsoleController {
    @Autowired
    ConsoleRepository consoleRepository;

    @GetMapping("/consoles")
    public List<Console> getConsoles() {
        return consoleRepository.findAll();
    }

    @GetMapping("/consoles/{id}")
    public Console getConsolesById(@PathVariable int id) {
        Optional<Console> returnVal = consoleRepository.findById(id);
        if(returnVal.isPresent()) {
            return returnVal.get();
        } else {
            return null;
        }
    }


    //SEARCH FUNCTIONALITY
    @GetMapping("/consoles/manufacturer/{manufacturer}")
    public List<Console>getConsolesByManufacturer(@PathVariable String manufacturer) {
        List<Console> returnVal = consoleRepository.findAllConsolesByManufacturer(manufacturer);
        if(returnVal.size() > 0) {
            return returnVal;
        } else {
            return null;
        }
    }

    //CRUD
    @PostMapping("/consoles")
    @ResponseStatus(HttpStatus.CREATED)
    public Console addConsole(@RequestBody Console console) {
        return consoleRepository.save(console);
    }

    @PutMapping("/consoles")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateConsole(@RequestBody Console console) {
        consoleRepository.save(console);
    }
    
    @DeleteMapping("/consoles/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteConsole(@PathVariable int id) {
        consoleRepository.deleteById(id);
    }


}
