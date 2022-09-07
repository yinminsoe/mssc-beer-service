package ym.course.micro.msscbeerservice.web.repository;


import org.springframework.data.repository.PagingAndSortingRepository;
import ym.course.micro.msscbeerservice.web.domain.Beer;

import java.util.UUID;

public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {
}
