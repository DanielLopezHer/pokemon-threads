package com.example.personal.apps.pokemon.service;

import com.example.personal.apps.pokemon.dto.AbstractPokemonDTO;
import com.example.personal.apps.pokemon.dto.PokemonDTO;
import com.example.personal.apps.pokemon.utils.threads.Master;
import com.example.personal.apps.pokemon.utils.threads.Worker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

        List<PokemonDTO> result = getPokemonDataByWorkers(pokemonIds);
        LOGGER.info("Lista de pokemón encontrada:");
        for (PokemonDTO pokemon :
                result) {
            LOGGER.info(pokemon.toString());
        }

        endTime = System.nanoTime();
        return "Duración: " + (endTime-startTime)/1e6 + " ms";
    }

    private List<PokemonDTO> getPokemonDataByWorkers(List<Integer> pokemonIds){
        Master master = new Master(new Worker(), pokemonIds.size() / 10 > 20? 10 : pokemonIds.size() / 10);

        for (Integer pokemonId : pokemonIds) {
            master.submit(new AbstractPokemonDTO(pokemonId));
        }

        master.execute();

        while (!master.isComplete()){
            //Espero a que terminen de ejecutarse todos los hilos
        }
        return new ArrayList<>(master.getResultMap());
    }

}
