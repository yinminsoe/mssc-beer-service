package ym.course.micro.msscbeerservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ym.course.micro.msscbeerservice.web.domain.Beer;
import ym.course.micro.msscbeerservice.web.exception.NotFoundException;
import ym.course.micro.msscbeerservice.web.mapper.BeerMapper;
import ym.course.micro.msscbeerservice.web.model.BeerDto;
import ym.course.micro.msscbeerservice.web.model.BeerPageList;
import ym.course.micro.msscbeerservice.web.repository.BeerRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;
    @Override
    public BeerDto getBeerById(UUID beerId,Boolean showInventoryOnhand) {
        Optional<Beer> beer= beerRepository.findById(beerId);
        if(showInventoryOnhand){
            return beerMapper.beerToBeerDtoWithInventory(beer.orElseThrow(NotFoundException::new));
        }else{
            return beerMapper.beerToBeerDto(beer.orElseThrow(NotFoundException::new));
        }

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
        beer.setUpc(beerDto.getUpc());

        return beerMapper.beerToBeerDto(beerRepository.save(beer));
    }

    @Override
    public BeerPageList listBeer(String beerName, String beerStyle, PageRequest pageRequest,Boolean showInventoryOnhand) {
        BeerPageList beerPageList;
        Page<Beer> beerPage;
            if(StringUtils.hasLength(beerName) && StringUtils.hasLength(beerStyle)){
                beerPage=beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
            }else if(StringUtils.hasLength(beerName) && !StringUtils.hasLength(beerStyle)){
                beerPage=beerRepository.findAllByBeerName(beerName, pageRequest);
            }else if(!StringUtils.hasLength(beerName) && StringUtils.hasLength(beerStyle)){
                beerPage=beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
            }else{
                beerPage=beerRepository.findAll(pageRequest);
            }

        if(showInventoryOnhand){
            beerPageList = new BeerPageList(beerPage
                    .getContent()
                    .stream()
                    .map(beerMapper::beerToBeerDtoWithInventory)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(beerPage.getPageable().getPageNumber(),
                                    beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());
        }else{
            beerPageList=new BeerPageList(
                    beerPage.getContent().stream().map(beerMapper::beerToBeerDto).collect(Collectors.toList()),
                    PageRequest.of(beerPage.getPageable().getPageNumber(), beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());
        }

        return beerPageList;
    }
}
