package com.SCraping.SpringBoot.AnimeFlvSCraping.services;

import com.SCraping.SpringBoot.AnimeFlvSCraping.entity.Anime;
import com.SCraping.SpringBoot.AnimeFlvSCraping.entity.AnimeEpisode;
import com.SCraping.SpringBoot.AnimeFlvSCraping.repositories.AnimeRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class AnimeServiceImpl implements IAnimeService{

    @Autowired
    private AnimeRepositoryImpl repository;

    @Override
    public Anime getAnimebyUrl(AnimeEpisode animeEpisode) throws IOException, InterruptedException {
        return repository.getAnimebyUrl(animeEpisode);
    }

    @Override
    public List<Anime> searchByTitle(String title) throws IOException {
        return repository.searchByTitle(title);
    }
}
