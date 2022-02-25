package guru.springframework.msscbeerservice.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerStyleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.math.BigDecimal;
import java.util.UUID;


@WebMvcTest(BeerController.class)
class BeerControllerTest {
    public static final String API_V_1_BEER = "/api/v1/beer";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private BeerDto beerDto;
    private String beerDtoJsonString;

    @BeforeEach
    void setUp() throws JsonProcessingException {
            beerDto=getBeerDto();
            beerDtoJsonString= objectMapper.writeValueAsString(beerDto);
    }

    @Test
    void getBeerById() throws Exception {
        mockMvc.perform(get(API_V_1_BEER+"/"+UUID.randomUUID()))
                .andExpect(status().isOk());

    }

    @Test
    void saveBeer() throws Exception {
        mockMvc.perform(post(API_V_1_BEER).contentType(MediaType.APPLICATION_JSON).content(beerDtoJsonString))
                .andExpect(status().isCreated());
    }

    @Test
    void updateBeer() throws Exception {
        mockMvc.perform(put(API_V_1_BEER+"/"+UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON).content(beerDtoJsonString))
                .andExpect(status().isNoContent());
    }
    public BeerDto getBeerDto(){return BeerDto.builder().beerName("New Beer ").id(null).upc(25L).beerStyle(BeerStyleEnum.IPA).price(BigDecimal.valueOf(3.50)).build();}

}