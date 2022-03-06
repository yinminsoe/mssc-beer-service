package guru.springframework.msscbeerservice.services;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.repositories.BeerRepository;
import guru.springframework.msscbeerservice.web.controllers.NotFoundException;
import guru.springframework.msscbeerservice.web.mappers.BeerMapper;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerPagedList;
import guru.springframework.msscbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;

    private final BeerMapper beerMapper;

    @Cacheable(cacheNames = {"beerListCache"}, condition = "#showInventoryOnHand == false")
    @Override
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand) {
        System.out.println("listBeers called ");
        BeerPagedList beerPagedList;
        Page<Beer> beerPage;

        String beerStyleString = beerStyle != null? beerStyle.toString():null;
        if (StringUtils.hasLength(beerName) && StringUtils.hasLength(beerStyleString)) {
            //search both
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if (StringUtils.hasLength(beerName) && !StringUtils.hasLength(beerStyleString)) {
            //search beer_service name
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if (!StringUtils.hasLength(beerName) && StringUtils.hasLength(beerStyleString)) {
            //search beer_service style
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        if(showInventoryOnHand){
            beerPagedList = new BeerPagedList(beerPage
                    .getContent()
                    .stream()
                    .map(beerMapper::beerToBeerDtoWithInventory)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(beerPage.getPageable().getPageNumber(),
                                    beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());
        }else{
            beerPagedList = new BeerPagedList(beerPage
                    .getContent()
                    .stream()
                    .map(beerMapper::beerToBeerDto)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(beerPage.getPageable().getPageNumber(),
                                    beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());
        }


        return beerPagedList;
    }

    @Override
    @Cacheable(cacheNames = {"beerCache"}, key = "#beerId", condition = "#showInventoryOnHand == false")
    public BeerDto getBeerById(UUID beerId, Boolean showInventoryOnHand) {
        System.out.println("getBeerById called ");
        if(showInventoryOnHand){
            return beerMapper.beerToBeerDtoWithInventory(
                    beerRepository.findById(beerId)
                            .orElseThrow(NotFoundException::new)
            );
        }else {
            return beerMapper.beerToBeerDto(
                    beerRepository.findById(beerId)
                            .orElseThrow(NotFoundException::new)
            );
        }

    }

    @Override
    public BeerDto saveBeer(BeerDto beerDto) {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDto)));
    }

    @Override
    public BeerDto updateBeer(BeerDto beerDto) {
        Beer beer = beerRepository.findById(beerDto.getId()).orElseThrow(NotFoundException::new);
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDto)));
    }
    @Cacheable(cacheNames = {"beerUpcCache"}, key = "#upc", condition = "#showInventoryOnHand == false")
    @Override
    public BeerDto getBeerByUpc(String upc, Boolean showInventoryOnHand) {
        System.out.println("getBeerByUpc called");
        if(showInventoryOnHand)
            return beerMapper.beerToBeerDtoWithInventory(beerRepository.findByUpc(upc));
        else
            return beerMapper.beerToBeerDto(beerRepository.findByUpc(upc));
    }
}
