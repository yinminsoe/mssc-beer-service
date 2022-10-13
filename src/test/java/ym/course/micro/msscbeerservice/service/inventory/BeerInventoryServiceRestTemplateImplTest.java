package ym.course.micro.msscbeerservice.service.inventory;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ym.course.micro.msscbeerservice.web.bootstrap.BeerLoader;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerInventoryServiceRestTemplateImplTest {

    @Autowired
    BeerInventoryService beerInventoryService;

    @BeforeEach
    void setUp(){

    }


    @Ignore
  //  @Test
    void getOnhandInventory() {
     Integer onHand= beerInventoryService.getOnhandInventory(BeerLoader.BEER_1_UUID);
       System.out.println("onHand ::"+onHand);
    }
}