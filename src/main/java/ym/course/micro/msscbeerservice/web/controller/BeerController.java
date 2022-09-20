package ym.course.micro.msscbeerservice.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ym.course.micro.msscbeerservice.service.BeerService;
import ym.course.micro.msscbeerservice.web.model.BeerDto;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping("/api/v1/beer")
@RestController
@RequiredArgsConstructor
public class BeerController {

    private final BeerService beerService;
    @GetMapping(value = {"/{beerId}"})
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId){
        //to do impl
        BeerDto beerDto= beerService.getBeerById(beerId);
        return new ResponseEntity<>(beerDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BeerDto> saveNewBeer(@RequestBody @Validated BeerDto beerDto){
        BeerDto savedNewBeer= beerService.saveNewBeer(beerDto);
        return new ResponseEntity<>(savedNewBeer, HttpStatus.CREATED);
    }

    @PutMapping(value = {"/{beerId}"})
    public ResponseEntity updateBeer(@PathVariable("beerId") UUID beerId, @RequestBody @Validated  BeerDto beerDto){
        BeerDto beerDto1=beerService.updateBeer(beerId, beerDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
