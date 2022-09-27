package ym.course.micro.msscbeerservice.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ym.course.micro.msscbeerservice.service.BeerService;
import ym.course.micro.msscbeerservice.web.model.BeerDto;
import ym.course.micro.msscbeerservice.web.model.BeerPageList;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping("/api/v1/")
@RestController
@RequiredArgsConstructor
public class BeerController {

    private final BeerService beerService;

    private static final Integer DEFAULT_PAGE_NUMBER=0;
    private static final Integer DEFAULT_PAGE_SIZE=20;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "beer")
    public ResponseEntity<BeerPageList> listBeers(
            @RequestParam(value = "pageNumber", required=false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "beerName", required = false) String beerName,
            @RequestParam(value = "beerStyle",required = false) String beerStyle,
            @RequestParam(value="", required = false) Boolean showInventoryOnhand
    ){
        if(showInventoryOnhand == null){
            showInventoryOnhand=false;
        }
        if(pageNumber == null || pageNumber < 0){
            pageNumber=DEFAULT_PAGE_NUMBER;
        }
        if(pageSize == null || pageSize < 0){
            pageSize = DEFAULT_PAGE_SIZE;
        }
        BeerPageList beerPageList=beerService.listBeer(beerName, beerStyle, PageRequest.of(pageNumber, pageSize),showInventoryOnhand);
        return new ResponseEntity<>(beerPageList, HttpStatus.OK);
    }
    @GetMapping(value = {"beer/{beerId}"})
    public ResponseEntity<BeerDto> getBeerById(
            @PathVariable("beerId") UUID beerId,
            @RequestParam(value = "showInventoryOnhand", required = false)Boolean showInventoryOnhand){
        if(showInventoryOnhand == null){
            showInventoryOnhand=false;
        }
        BeerDto beerDto= beerService.getBeerById(beerId,showInventoryOnhand);
        return new ResponseEntity<>(beerDto, HttpStatus.OK);
    }
    @GetMapping(value = {"beerupc/{upc}"})
    public ResponseEntity<BeerDto> getBeerByUpc(
            @PathVariable("upc") String upc){
        BeerDto beerDto= beerService.getBeerByUpc(upc);
        return new ResponseEntity<>(beerDto, HttpStatus.OK);
    }

    @PostMapping(path = "beer")
    public ResponseEntity<BeerDto> saveNewBeer(@RequestBody @Validated BeerDto beerDto){
        BeerDto savedNewBeer= beerService.saveNewBeer(beerDto);
        return new ResponseEntity<>(savedNewBeer, HttpStatus.CREATED);
    }

    @PutMapping(value = {"beer/{beerId}"})
    public ResponseEntity updateBeer(@PathVariable("beerId") UUID beerId, @RequestBody @Validated  BeerDto beerDto){
        BeerDto beerDto1=beerService.updateBeer(beerId, beerDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
