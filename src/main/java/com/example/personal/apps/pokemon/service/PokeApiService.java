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

    public void getInfoPokemon(){
        LOGGER.info("Iniciando la búsqueda de los pokemón.");
        List<Integer> pokemonIds = new ArrayList<>();
        List<PokemonDTO> pokemons = new ArrayList<>();
        for(int i = 1; i < 10; i++){
            pokemonIds.add(i);
        }

        for (Integer id : pokemonIds) {
            PokemonDTO currentPokemon = new PokemonDTO();
            currentPokemon = consumirServicio("", id);
            LOGGER.info("Información del pokemón recibida: ");
            LOGGER.info(currentPokemon.toString());
        }

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
