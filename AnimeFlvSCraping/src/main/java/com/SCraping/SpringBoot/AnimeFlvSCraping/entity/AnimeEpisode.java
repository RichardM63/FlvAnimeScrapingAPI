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
    private String cap;
    private String thumbnail;
    private String urlPaginaCapitulo;
    private String urlAnimeDescrtiption;
    private String nextEpisode;
    private String lastEpisode;
    private Map<String,String> options;

    public AnimeEpisode(String title,String urlPaginaCapitulo) {
        this.title = title;
        this.urlPaginaCapitulo = urlPaginaCapitulo;
    }

    public AnimeEpisode(String urlAnimeDescrtiption) {
        this.urlAnimeDescrtiption = urlAnimeDescrtiption;
    }

    public AnimeEpisode(String title, String cap, String thumbnail, String urlPaginaCapitulo) {
        this.title = title;
        this.cap = cap;
        this.thumbnail = thumbnail;
        this.urlPaginaCapitulo = urlPaginaCapitulo;
    }

    public AnimeEpisode(String title, String cap, String thumbnail, String urlPaginaCapitulo, String urlAnimeDescrtiption) {
        this.title = title;
        this.cap = cap;
        this.thumbnail = thumbnail;
        this.urlPaginaCapitulo = urlPaginaCapitulo;
        this.urlAnimeDescrtiption =urlAnimeDescrtiption;
    }
}
