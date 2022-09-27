package ym.course.micro.msscbeerservice.service;

import org.springframework.data.domain.PageRequest;
import ym.course.micro.msscbeerservice.web.model.BeerDto;
import ym.course.micro.msscbeerservice.web.model.BeerPageList;

import java.util.UUID;

public interface BeerService {
    BeerDto getBeerById(UUID beerId,Boolean showInventoryOnhand);

    BeerDto saveNewBeer(BeerDto beerDto);

    BeerDto updateBeer(UUID beerId, BeerDto beerDto);

    BeerPageList listBeer(String beerName, String beerStyle, PageRequest of,Boolean showInventoryOnhand);

    BeerDto getBeerByUpc(String upc);
}
