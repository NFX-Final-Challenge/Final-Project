package com.company.FinalProject.repositories;

import com.company.FinalProject.models.Tshirt;
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
public class TshirtRepositoryTest
{
    @Autowired
    TshirtRepository tshirtRepository;

    @Before
    public void setUp() throws Exception
    {
        tshirtRepository.deleteAll();
    }

    @Test
    public void shouldAddGetDeleteTshirt()
    {
        Tshirt tshirt= new Tshirt();
        tshirt.setSize("Large");
        tshirt.setColor("Black");
        tshirt.setDescription("Super Mario Graphic Tee");
        tshirt.setPrice(new BigDecimal(15.45));
        tshirt.setQuantity(15);

        tshirt = tshirtRepository.save(tshirt);

        Optional<Tshirt> testTshirt = tshirtRepository.findById(tshirt.getT_shirt_id());
        assertEquals(testTshirt.get(),tshirt);

        tshirtRepository.deleteById(tshirt.getT_shirt_id());
        testTshirt = tshirtRepository.findById(tshirt.getT_shirt_id());
        assertFalse(testTshirt.isPresent());
    }

    @Test
    public void shouldUpdateTshirt()
    {
        Tshirt tshirt= new Tshirt();
        tshirt.setSize("Large");
        tshirt.setColor("Black");
        tshirt.setDescription("Super Mario Graphic Tee");
        tshirt.setPrice(new BigDecimal(15.99));
        tshirt.setQuantity(15);

        tshirt = tshirtRepository.save(tshirt);

        tshirt.setQuantity(13);
        tshirtRepository.save(tshirt);

        Optional<Tshirt> testTshirt = tshirtRepository.findById(tshirt.getT_shirt_id());
        assertEquals(testTshirt.get(),tshirt);
    }

    @Test
    public void shouldFindAllTshirts()
    {
        Tshirt tshirt= new Tshirt();
        tshirt.setSize("Large");
        tshirt.setColor("Black");
        tshirt.setDescription("Super Mario Graphic Tee");
        tshirt.setPrice(new BigDecimal(15.99));
        tshirt.setQuantity(15);
        tshirt = tshirtRepository.save(tshirt);

        Tshirt tshirt2= new Tshirt();
        tshirt2.setSize("Medium");
        tshirt2.setColor("Black");
        tshirt2.setDescription("Super Mario Graphic Tee");
        tshirt2.setPrice(new BigDecimal(15.99));
        tshirt2.setQuantity(23);
        tshirt2 = tshirtRepository.save(tshirt2);

        Tshirt tshirt3 = new Tshirt();
        tshirt3.setSize("Small");
        tshirt3.setColor("Blue");
        tshirt3.setDescription("Super Mario Graphic Tee");
        tshirt3.setPrice(new BigDecimal(15.99));
        tshirt3.setQuantity(7);
        tshirt3 = tshirtRepository.save(tshirt3);

        List<Tshirt> allTshirts = tshirtRepository.findAll();
        assertEquals(allTshirts.size(),3);
    }

    @Test
    public void shouldFindAllTshirtsByColor()
    {
        Tshirt tshirt= new Tshirt();
        tshirt.setSize("Large");
        tshirt.setColor("Black");
        tshirt.setDescription("Super Mario Graphic Tee");
        tshirt.setPrice(new BigDecimal(15.99));
        tshirt.setQuantity(15);
        tshirt = tshirtRepository.save(tshirt);

        Tshirt tshirt2= new Tshirt();
        tshirt2.setSize("Medium");
        tshirt2.setColor("Black");
        tshirt2.setDescription("Super Mario Graphic Tee");
        tshirt2.setPrice(new BigDecimal(15.99));
        tshirt2.setQuantity(23);
        tshirt2 = tshirtRepository.save(tshirt2);

        Tshirt tshirt3 = new Tshirt();
        tshirt3.setSize("Small");
        tshirt3.setColor("Blue");
        tshirt3.setDescription("Super Mario Graphic Tee");
        tshirt3.setPrice(new BigDecimal(15.99));
        tshirt3.setQuantity(7);
        tshirt3 = tshirtRepository.save(tshirt3);

        List<Tshirt> allTshirts = tshirtRepository.findTshirtByColor("Black");
        assertEquals(allTshirts.size(),2);
    }

    @Test
    public void shouldFindAllTshirtsBySize()
    {
        Tshirt tshirt= new Tshirt();
        tshirt.setSize("Large");
        tshirt.setColor("Black");
        tshirt.setDescription("Super Mario Graphic Tee");
        tshirt.setPrice(new BigDecimal(15.99));
        tshirt.setQuantity(15);
        tshirt = tshirtRepository.save(tshirt);

        Tshirt tshirt2= new Tshirt();
        tshirt2.setSize("Medium");
        tshirt2.setColor("Black");
        tshirt2.setDescription("Super Mario Graphic Tee");
        tshirt2.setPrice(new BigDecimal(15.99));
        tshirt2.setQuantity(23);
        tshirt2 = tshirtRepository.save(tshirt2);

        Tshirt tshirt3 = new Tshirt();
        tshirt3.setSize("Small");
        tshirt3.setColor("Blue");
        tshirt3.setDescription("Super Mario Graphic Tee");
        tshirt3.setPrice(new BigDecimal(15.99));
        tshirt3.setQuantity(7);
        tshirt3 = tshirtRepository.save(tshirt3);

        List<Tshirt> allTshirts = tshirtRepository.findTshirtBySize("Small");
        assertEquals(allTshirts.size(),1);
    }

}
