package ym.course.micro.msscbeerservice.web.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ym.course.micro.msscbeerservice.web.domain.Beer;
import ym.course.micro.msscbeerservice.web.model.BeerPageList;

import java.util.UUID;

public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {
    public Page<Beer> findAllByBeerName(String beerName, Pageable pageable);
    public Page<Beer> findAllByBeerStyle(String beerStyle, Pageable pageable);

    public Page<Beer> findAllByBeerNameAndBeerStyle(String beerName, String beerStyle,Pageable pageable);
}
