package com.SCraping.SpringBoot.AnimeFlvSCraping.services;

import com.SCraping.SpringBoot.AnimeFlvSCraping.entity.Anime;
import com.SCraping.SpringBoot.AnimeFlvSCraping.entity.AnimeEpisode;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IAnimeService {
    Anime getAnimebyUrl(AnimeEpisode animeEpisode) throws IOException, InterruptedException, ExecutionException;
    List<Anime> searchByTitle(String title) throws IOException;
}
