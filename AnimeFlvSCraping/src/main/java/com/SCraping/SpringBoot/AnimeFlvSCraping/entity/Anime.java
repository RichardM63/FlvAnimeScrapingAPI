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
    private String urlAnimeDescrtiption;
    private List<AnimeEpisode> episodes;
    private String thumbnail;
    private Boolean state;
    private Float rate;

    public Anime(String title, String synopsis,String urlAnimeDescrtiption, String thumbnail, Float rate) {
        this.title = title;
        this.synopsis = synopsis;
        this.urlAnimeDescrtiption =urlAnimeDescrtiption;
        this.thumbnail = thumbnail;
        this.rate = rate;
    }
}
