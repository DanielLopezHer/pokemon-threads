package com.example.personal.apps.pokemon.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PokemonDTO {
    private Integer id;
    private String name;
    private Double height;
    private Double weight;

    @Override
    public String toString() {
        return "PokemonDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                '}';
    }
}
