package com.SCraping.SpringBoot.AnimeFlvSCraping.services;

import com.SCraping.SpringBoot.AnimeFlvSCraping.entity.AnimeEpisode;
import com.SCraping.SpringBoot.AnimeFlvSCraping.repositories.AnimeFlvRespositoryImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnimeFlvServiceImpl implements IAnimeFlvService{

    @Autowired
    private AnimeFlvRespositoryImpl respository;

    private static final String BASE_URL = "https://www3.animeflv.net";

    public List<AnimeEpisode> getLatestEpisodes(){
        return respository.getLatestEpisodes();
    }

    public AnimeEpisode getEpisodeData(AnimeEpisode animeEpisode){
        return respository.getEpisodeData(animeEpisode);
    }
}
