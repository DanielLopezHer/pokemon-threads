package com.example.personal.apps.pokemon.controller;

import com.example.personal.apps.pokemon.service.PokeApiService;
import com.example.personal.apps.pokemon.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.BASE_URL_POKEAPI)
public class PokeApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PokeApiController.class);
    private final PokeApiService pokeApiService;

    @Autowired
    public PokeApiController(PokeApiService pokeApiService) {
        this.pokeApiService = pokeApiService;
    }

    @GetMapping()
    public String getPokeInfo(){
        LOGGER.info("Llamado al endpoint de búsqueda simple de información de pokemón");
        pokeApiService.getInfoPokemon();
        return "Búsqueda terminada.";
    }
}
