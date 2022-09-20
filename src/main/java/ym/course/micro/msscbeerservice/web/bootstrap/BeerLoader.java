package ym.course.micro.msscbeerservice.web.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ym.course.micro.msscbeerservice.web.domain.Beer;
import ym.course.micro.msscbeerservice.web.repository.BeerRepository;

import java.math.BigDecimal;

@Component
public class BeerLoader implements CommandLineRunner {
    public static final String BEER_1_UPC="0631234200036";
    public static final String BEER_2_UPC="0631234300019";
    public static final String BEER_3_UPC="0083783375213";
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
                    .upc(BEER_1_UPC)
                    .price(new BigDecimal("12.95"))
                    .build());

            beerRepository.save(Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle("PALE_ALE")
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .upc(BEER_2_UPC)
                    .price(new BigDecimal("11.95"))
                    .build());

            beerRepository.save(Beer.builder()
                    .beerName("Galaxy Cat3")
                    .beerStyle("PALE_ALE")
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .upc(BEER_3_UPC)
                    .price(new BigDecimal("13.95"))
                    .build());
        }
    }

}
