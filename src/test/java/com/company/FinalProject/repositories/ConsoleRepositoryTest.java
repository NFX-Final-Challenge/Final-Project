package com.company.FinalProject.repositories;

import com.company.FinalProject.models.Console;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ConsoleRepositoryTest
{
    @Autowired
    ConsoleRepository consoleRepository;

    @Before
    public void setUp() throws Exception
    {
        consoleRepository.deleteAll();
    }

    @Test
    public void shouldAddGetDeleteConsole()
    {
        Console console = new Console();
        console.setModel("Playstation 5");
        console.setManufacturer("Sony");
        console.setMemoryAmount("825 GB");
        console.setProcessor("AMD Zen 2 CPU");
        console.setPrice(new BigDecimal("693.80"));
        console.setQuantity(2);

        console = consoleRepository.save(console);

        Optional<Console> testConsole = consoleRepository.findById(console.getId());
        assertEquals(testConsole.get(),console);

        consoleRepository.deleteById(console.getId());
        testConsole = consoleRepository.findById(console.getId());
        assertFalse(testConsole.isPresent());
    }

    @Test
    public void shouldUpdateConsole()
    {
        Console console = new Console();
        console.setModel("Playstation 5");
        console.setManufacturer("Sony");
        console.setMemoryAmount("825 GB");
        console.setProcessor("AMD Zen 2 CPU");
        console.setPrice(new BigDecimal("693.80"));
        console.setQuantity(2);

        console = consoleRepository.save(console);

        console.setQuantity(1);
        consoleRepository.save(console);

        Optional<Console> testConsole = consoleRepository.findById(console.getId());
        assertEquals(testConsole.get(), console);
    }

    @Test
    public void shouldFindAllConsoles()
    {
        Console console = new Console();
        console.setModel("Playstation 5");
        console.setManufacturer("Sony");
        console.setMemoryAmount("825 GB");
        console.setProcessor("AMD Zen 2 CPU");
        console.setPrice(new BigDecimal("693.80"));
        console.setQuantity(2);
        console = consoleRepository.save(console);

        Console console2 = new Console();
        console2.setModel("Playstation 5 Digital Edition");
        console2.setManufacturer("Sony");
        console2.setMemoryAmount("825 GB");
        console2.setProcessor("AMD Zen 2 CPU");
        console2.setPrice(new BigDecimal("569.99"));
        console2.setQuantity(7);
        console2 = consoleRepository.save(console2);

        Console console3 = new Console();
        console3.setModel("X Box Series X");
        console3.setManufacturer("Microsoft");
        console3.setMemoryAmount("1 TB");
        console3.setProcessor("AMD Zen 2 CPU");
        console3.setPrice(new BigDecimal("499.00"));
        console3.setQuantity(10);
        console3 = consoleRepository.save(console3);

        List<Console> allConsoles = consoleRepository.findAll();
        assertEquals(allConsoles.size(),3);
    }

    @Test
    public void shouldFindAllConsolesByManufacturer()
    {
        Console console = new Console();
        console.setModel("Playstation 5");
        console.setManufacturer("Sony");
        console.setMemoryAmount("825 GB");
        console.setProcessor("AMD Zen 2 CPU");
        console.setPrice(new BigDecimal("693.80"));
        console.setQuantity(2);
        console = consoleRepository.save(console);

        Console console2 = new Console();
        console2.setModel("Playstation 5 Digital Edition");
        console2.setManufacturer("Sony");
        console2.setMemoryAmount("825 GB");
        console2.setProcessor("AMD Zen 2 CPU");
        console2.setPrice(new BigDecimal("569.99"));
        console2.setQuantity(7);
        console2 = consoleRepository.save(console2);

        Console console3 = new Console();
        console3.setModel("X Box Series X");
        console3.setManufacturer("Microsoft");
        console3.setMemoryAmount("1 TB");
        console3.setProcessor("AMD Zen 2 CPU");
        console3.setPrice(new BigDecimal("499.00"));
        console3.setQuantity(10);
        console3 = consoleRepository.save(console3);

        List<Console> listConsoles = consoleRepository.findAllConsolesByManufacturer("Sony");
        assertEquals(listConsoles.size(),2);
    }

}