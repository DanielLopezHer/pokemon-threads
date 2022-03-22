package com.example.personal.apps.pokemon.utils.threads;



import com.example.personal.apps.pokemon.dto.PokemonDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;

public class Worker implements Runnable {

    protected Queue<PokemonDTO> workQueue;
    protected Queue<PokemonDTO> resultList;

    private static final Logger log = LoggerFactory.getLogger(Worker.class);

    public void setWorkQueue(Queue<PokemonDTO> workQueue) {
        this.workQueue = workQueue;
    }

    public void setResultMap(Queue<PokemonDTO> resultList) {
        this.resultList = resultList;
    }

    public void handle(PokemonDTO am) {

    }

    @Override
    public void run() {
        while(workQueue.peek() != null){
            handle(workQueue.poll());
        }
    }
}
