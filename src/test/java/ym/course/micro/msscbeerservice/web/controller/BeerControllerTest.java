package ym.course.micro.msscbeerservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ym.course.micro.msscbeerservice.web.model.BeerDto;
import ym.course.micro.msscbeerservice.web.model.BeerStyleEnum;

import java.util.UUID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class BeerControllerTest {
    private static final String BEER_PATH_V1="/api/v1/beer/";
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    BeerDto beerDto;
    @BeforeEach
    void setUp() {
        beerDto= BeerDto.builder()
                .id(UUID.randomUUID())
                .beerName("Beer Name")
                .beerStyle(BeerStyleEnum.IPA)
                .build();
    }
    @Test
    void getBeerById() throws Exception {
        mockMvc.perform(
                get(BEER_PATH_V1+ UUID.randomUUID()))
                .andExpect(status().isOk());
    }

    @Test
    void saveNewBeer() throws Exception {
        String beerDtoJson=objectMapper.writeValueAsString(beerDto);
        mockMvc.perform(post(BEER_PATH_V1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isCreated());

    }

    @Test
    void updateBeer() throws Exception {
        String beerDtoJson=objectMapper.writeValueAsString(beerDto);
        mockMvc.perform(
                put(BEER_PATH_V1+UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDto)))
                .andExpect(status().isNoContent());
    }
}