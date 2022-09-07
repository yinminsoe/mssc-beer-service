package ym.course.micro.msscbeerservice.web.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ym.course.micro.msscbeerservice.web.domain.Beer;
import ym.course.micro.msscbeerservice.web.repository.BeerRepository;

import java.math.BigDecimal;

@Component
public class BeerLoader implements CommandLineRunner {

    private final BeerRepository beerRepository;

    public BeerLoader(BeerRepository beerRepository){
        this.beerRepository=beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadBeers();
    }

    private void loadBeers() {
        if(beerRepository.count() == 0){
            beerRepository.save(Beer.builder()
                    .beerName("Mongo Bobs")
                    .beerStyle("IPA")
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .upc(37300000001L)
                    .price(new BigDecimal("12.95"))
                    .build());

            beerRepository.save(Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle("PALE_ALE")
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .upc(37300000002L)
                    .price(new BigDecimal("11.95"))
                    .build());
        }
    }

}
