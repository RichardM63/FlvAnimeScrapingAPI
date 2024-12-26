package com.SCraping.SpringBoot.AnimeFlvSCraping.services;

import com.SCraping.SpringBoot.AnimeFlvSCraping.entity.AnimeEpisode;

import java.util.List;

public interface IAnimeFlvService {
    List<AnimeEpisode> getLatestEpisodes();
    AnimeEpisode getEpisodeData(String title);
}
