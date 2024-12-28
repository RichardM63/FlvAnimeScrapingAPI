package com.SCraping.SpringBoot.AnimeFlvSCraping.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class Anime {
    private String title;
    private String synopsis;
    private List<AnimeEpisode> episodes;
    private String thumbnail;
    private Boolean state;
    private Float rate;
}
