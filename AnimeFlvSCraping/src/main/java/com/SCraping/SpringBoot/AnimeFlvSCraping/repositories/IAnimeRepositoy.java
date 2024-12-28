package com.SCraping.SpringBoot.AnimeFlvSCraping.repositories;

import com.SCraping.SpringBoot.AnimeFlvSCraping.entity.Anime;
import com.SCraping.SpringBoot.AnimeFlvSCraping.entity.AnimeEpisode;

import java.io.IOException;

public interface IAnimeRepositoy {
    Anime getAnimebyTitle(AnimeEpisode episode) throws IOException, InterruptedException;
}
