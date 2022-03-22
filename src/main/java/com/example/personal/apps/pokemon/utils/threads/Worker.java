package com.example.personal.apps.pokemon.utils.threads;



import com.example.personal.apps.pokemon.dto.AbstractPokemonDTO;
import com.example.personal.apps.pokemon.dto.PokemonDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Queue;

public class Worker implements Runnable {

    protected Queue<AbstractPokemonDTO> workQueue;
    protected Queue<PokemonDTO> resultList;

    private static final Logger log = LoggerFactory.getLogger(Worker.class);

    public void setWorkQueue(Queue<AbstractPokemonDTO> workQueue) {
        this.workQueue = workQueue;
    }

    public void setResultMap(Queue<PokemonDTO> resultList) {
        this.resultList = resultList;
    }

    public void handle(AbstractPokemonDTO am) {
        PokemonDTO pokemonDTO = consumirServicio("", am.getId());
        resultList.add(pokemonDTO);
    }

    @Override
    public void run() {
        while(workQueue.peek() != null){
            handle(workQueue.poll());
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
