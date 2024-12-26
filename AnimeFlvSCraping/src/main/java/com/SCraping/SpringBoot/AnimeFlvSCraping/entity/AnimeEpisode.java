package com.SCraping.SpringBoot.AnimeFlvSCraping.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnimeEpisode {
    private String title;
    private String thumbnail;
    private String urlPaginaCapitulo;
    private Map<String,String> options;

    public AnimeEpisode(String title, String thumbnail, String urlPaginaCapitulo) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.urlPaginaCapitulo = urlPaginaCapitulo;
    }
}
