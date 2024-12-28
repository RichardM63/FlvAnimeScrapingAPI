package com.SCraping.SpringBoot.AnimeFlvSCraping.services;

import com.SCraping.SpringBoot.AnimeFlvSCraping.entity.Anime;
import com.SCraping.SpringBoot.AnimeFlvSCraping.entity.AnimeEpisode;
import com.SCraping.SpringBoot.AnimeFlvSCraping.repositories.AnimeRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AnimeServiceImpl implements IAnimeService{

    @Autowired
    private AnimeRepositoryImpl repository;

    @Override
    public Anime getAnimebyTitle(AnimeEpisode animeEpisode) throws IOException, InterruptedException {
        return repository.getAnimebyTitle(animeEpisode);
    }
}
