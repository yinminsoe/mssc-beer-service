package ym.course.micro.msscbeerservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ym.course.micro.msscbeerservice.web.domain.Beer;
import ym.course.micro.msscbeerservice.web.exception.NotFoundException;
import ym.course.micro.msscbeerservice.web.mapper.BeerMapper;
import ym.course.micro.msscbeerservice.web.model.BeerDto;
import ym.course.micro.msscbeerservice.web.repository.BeerRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;
    @Override
    public BeerDto getBeerById(UUID beerId) {
        return beerMapper.beerToBeerDto(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDto)));
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
        Beer beer=beerRepository.findById(beerId).orElseThrow(NotFoundException::new);
        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().toString());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(Long.valueOf(beerDto.getUpc()));

        return beerMapper.beerToBeerDto(beerRepository.save(beer));
    }
}
