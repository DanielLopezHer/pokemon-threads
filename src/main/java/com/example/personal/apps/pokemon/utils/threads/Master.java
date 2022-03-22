package com.example.personal.apps.pokemon.utils.threads;

import com.example.personal.apps.pokemon.dto.PokemonDTO;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Master {

    protected Queue<PokemonDTO> workQueue =
            new ConcurrentLinkedQueue<>();

    protected Map<String, Thread> threadMap =
            new HashMap<>();

    protected Queue<PokemonDTO> resultMap =
            new ConcurrentLinkedQueue<>();

    public Master(Worker worker, int countWorker) {
        worker.setWorkQueue(workQueue);
        worker.setResultMap(resultMap);
        for(int i=0; i<countWorker; i++) {
            threadMap.put(Integer.toString(i),
                    new Thread(worker, Integer.toString(i)));
        }
    }

    public boolean isComplete() {
        for(Map.Entry<String, Thread> entry : threadMap.entrySet()) {
            if(entry.getValue().getState() != Thread.State.TERMINATED){
                return false;
            }
        }
        return true;
    }

    public void submit(PokemonDTO job) {
        workQueue.add(job);
    }

    public Queue<PokemonDTO> getResultMap() {
        return resultMap;
    }

    //Execute all Worker processes and process them
    public void execute() {
        for(Map.Entry<String, Thread> entry : threadMap.entrySet()) {
            entry.getValue().start();
        }
    }
}
