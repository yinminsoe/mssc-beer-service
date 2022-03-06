package guru.springframework.msscbeerservice.web.controllers;

import guru.springframework.msscbeerservice.services.BeerService;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerPagedList;
import guru.springframework.msscbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(BeerController.API_V_1_BEER)
public class BeerController {
    public static final String API_V_1_BEER = "/api/v1/beer";

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    private final BeerService beerService;

    @GetMapping(produces = { "application/json" })
    public ResponseEntity<BeerPagedList> listBeers(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                   @RequestParam(value = "beerName", required = false) String beerName,
                                                   @RequestParam(value = "beerStyle", required = false) BeerStyleEnum beerStyle,
                                                   @RequestParam(value= "showInventoryOnHand", required = false) Boolean showInventoryOnHand
                                            ){

        if(showInventoryOnHand == null){
            showInventoryOnHand=false;
        }

        if (pageNumber == null || pageNumber < 0){
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        BeerPagedList beerList = beerService.listBeers(beerName, beerStyle, PageRequest.of(pageNumber, pageSize), showInventoryOnHand);

        return new ResponseEntity<>(beerList, HttpStatus.OK);
    }

    @GetMapping({"/upc/{upc}"})
    public ResponseEntity<BeerDto> getBeerByUpc(@PathVariable("upc") String upc, @RequestParam(name = "showInventoryOnHand", required = false) Boolean showInventoryOnHand){
        if(showInventoryOnHand == null){
            showInventoryOnHand=false;
        }
        return new ResponseEntity<>(beerService.getBeerByUpc(upc, showInventoryOnHand), HttpStatus.OK);
    }


    @GetMapping({"/{beerId}"})
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId, @RequestParam(name = "showInventoryOnHand", required = false) Boolean showInventoryOnHand){
        if(showInventoryOnHand == null){
            showInventoryOnHand=false;
        }
        return new ResponseEntity<>(beerService.getBeerById(beerId, showInventoryOnHand), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveBeer(@Valid  @RequestBody BeerDto beerDto){
        return new ResponseEntity(beerService.saveBeer(beerDto), HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity updateBeer(@PathVariable("beerId") UUID beerId, @Valid @RequestBody BeerDto beerDto){
        beerDto.setId(beerId);
        return new ResponseEntity(beerService.updateBeer(beerDto),HttpStatus.NO_CONTENT);
    }

 }
