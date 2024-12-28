package com.SCraping.SpringBoot.AnimeFlvSCraping.services;

import com.SCraping.SpringBoot.AnimeFlvSCraping.entity.Anime;
import com.SCraping.SpringBoot.AnimeFlvSCraping.entity.AnimeEpisode;

import java.io.IOException;

public interface IAnimeService {
    Anime getAnimebyTitle(AnimeEpisode animeEpisode) throws IOException, InterruptedException;
}
