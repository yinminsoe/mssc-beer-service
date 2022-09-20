package ym.course.micro.msscbeerservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ym.course.micro.msscbeerservice.service.BeerService;
import ym.course.micro.msscbeerservice.web.bootstrap.BeerLoader;
import ym.course.micro.msscbeerservice.web.model.BeerDto;
import ym.course.micro.msscbeerservice.web.model.BeerStyleEnum;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class BeerControllerTest {
    private static final String BEER_PATH_V1="/api/v1/beer/";
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    BeerDto beerDto;
    @BeforeEach
    void setUp() {
        beerDto= BeerDto.builder()
                .beerName("Beer Name")
                .beerStyle(BeerStyleEnum.IPA)
                .upc(BeerLoader.BEER_1_UPC)
                .price(BigDecimal.valueOf(12.85))
                .build();
    }
    @Test
    void getBeerById() throws Exception {
        given(beerService.getBeerById(any(UUID.class))).willReturn(beerDto);
        mockMvc.perform(
                get(BEER_PATH_V1+ UUID.randomUUID()))
                .andExpect(status().isOk());
    }

    @Test
    void saveNewBeer() throws Exception {
        given(beerService.saveNewBeer(any(BeerDto.class))).willReturn(beerDto);
        String beerDtoJson=objectMapper.writeValueAsString(beerDto);
        mockMvc.perform(post(BEER_PATH_V1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isCreated());

    }

    @Test
    void updateBeer() throws Exception {
        given(beerService.updateBeer(beerDto.getId(), beerDto)).willReturn(beerDto);
        String beerDtoJson=objectMapper.writeValueAsString(beerDto);
        mockMvc.perform(
                put(BEER_PATH_V1+UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDto)))
                .andExpect(status().isNoContent());
    }
}