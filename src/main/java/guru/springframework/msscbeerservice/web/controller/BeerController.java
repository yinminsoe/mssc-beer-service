package guru.springframework.msscbeerservice.web.controller;

import guru.springframework.msscbeerservice.web.model.BeerDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(BeerController.API_V_1_BEER)
public class BeerController {

    public static final String API_V_1_BEER = "/api/v1/beer";

    @GetMapping({"/{beerId}"})
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId){
        return new ResponseEntity<>(BeerDto.builder().id(UUID.randomUUID()).build(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveBeer(@Valid  @RequestBody BeerDto beerDto){
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity updateBeer(@PathVariable("beerId") UUID beerId, @Valid @RequestBody BeerDto beerDto){

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
