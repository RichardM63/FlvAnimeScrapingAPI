package com.SCraping.SpringBoot.AnimeFlvSCraping.repositories;

import com.SCraping.SpringBoot.AnimeFlvSCraping.entity.AnimeEpisode;

import java.util.List;

public interface IAnimeFlvRepository {
    List<AnimeEpisode> getLatestEpisodes();
    AnimeEpisode getEpisodeData(String title);
}
