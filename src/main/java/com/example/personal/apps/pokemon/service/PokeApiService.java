package com.example.personal.apps.pokemon.service;

import com.example.personal.apps.pokemon.dto.PokemonDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class PokeApiService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PokeApiService.class);

    public String getInfoPokemon(){
        LOGGER.info("Iniciando la búsqueda de los pokemón.");
        List<Integer> pokemonIds = new ArrayList<>();
        long startTime = 0;
        long endTime = 0;
        for(int i = 1; i < 899; i++){
            pokemonIds.add(i);
        }

        startTime = System.nanoTime();
        for (Integer id : pokemonIds) {
            PokemonDTO currentPokemon = new PokemonDTO();
            currentPokemon = consumirServicio("", id);
            LOGGER.info("Información del pokemón recibida: ");
            LOGGER.info(currentPokemon.toString());
        }

        endTime = System.nanoTime();
        return "Duración: " + (endTime-startTime)/1e6 + " ms";
    }

    private PokemonDTO consumirServicio(String request, Integer id) {
        String baseUrl = "https://pokeapi.co/api/v2/pokemon/%d";
        String URL = String.format(baseUrl, id);
        RestTemplate cliente = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("text", "plain", StandardCharsets.UTF_8));
        HttpEntity<String> entity = new HttpEntity<>(request, headers);
        return cliente.exchange(URL, HttpMethod.GET, entity, PokemonDTO.class).getBody();
    }

}
