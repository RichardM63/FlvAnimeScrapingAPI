package com.SCraping.SpringBoot.AnimeFlvSCraping.repositories;

import com.SCraping.SpringBoot.AnimeFlvSCraping.entity.AnimeEpisode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class AnimeFlvRespositoryImpl implements IAnimeFlvRepository{

    private static final String BASE_URL = "https://www3.animeflv.net";
    private static List<AnimeEpisode> episodes;


    public AnimeFlvRespositoryImpl() throws IOException {
        episodes = new ArrayList<>();
        Document doc = Jsoup.connect(BASE_URL).get();
        Elements elements = doc.select(".ListEpisodios li");

        for (Element element : elements) {
            String title = element.select(".Title").text();
            String episodeUrl = BASE_URL + element.select("a").attr("href");
            String thumbnail = element.select("img").attr("src");

            episodes.add(new AnimeEpisode(title,thumbnail,episodeUrl));
        }
    }

    @Override
    public List<AnimeEpisode> getLatestEpisodes() {
        return episodes;
    }

    @Override
    public AnimeEpisode getEpisodeData(String title) {
        System.setProperty("webdriver.chrome.driver", "/home/ubuntu/drivers/chromedriver");

        ChromeOptions optionsChrome = new ChromeOptions();
        optionsChrome.addArguments("--headless"); // Modo headless
        optionsChrome.addArguments("--no-sandbox");
        optionsChrome.addArguments("--disable-dev-shm-usage");
        optionsChrome.addArguments("--remote-debugging-port=9222");

        WebDriver driver = new ChromeDriver(optionsChrome);
        try {
        episodes.stream().filter(animeEpisode -> animeEpisode.getTitle().equals(title)).peek(animeEpisode -> {
            driver.get(animeEpisode.getUrlPaginaCapitulo());
        }).findAny();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> optionElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("ul.CapiTnv li")));

        Map<String,String> options = new HashMap<>();

        for (WebElement option : optionElements) {
            String optionTitle = option.getAttribute("title");
            if(!optionTitle.equals("Netu")) {
                option.click();
                WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("iframe")));
                if (optionTitle != null && !optionTitle.isEmpty()) {
                    options.put(optionTitle, iframe.getAttribute("src"));
                } else {
                    System.out.println("Opcion invalid");
                }
            }
        }
            return episodes.stream().filter(animeEpisode -> animeEpisode.getTitle().equals(title))
                    .peek(episode->episode.setOptions(options))
                    .findAny().orElseThrow();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            driver.quit();
        }
    }
}
