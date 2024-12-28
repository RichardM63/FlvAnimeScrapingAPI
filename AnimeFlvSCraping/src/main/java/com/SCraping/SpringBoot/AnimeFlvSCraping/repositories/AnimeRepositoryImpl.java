package com.SCraping.SpringBoot.AnimeFlvSCraping.repositories;

import com.SCraping.SpringBoot.AnimeFlvSCraping.entity.Anime;
import com.SCraping.SpringBoot.AnimeFlvSCraping.entity.AnimeEpisode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class AnimeRepositoryImpl implements IAnimeRepositoy {

    private static final String BASE_URL = "https://www3.animeflv.net/anime/";

    @Override
    public Anime getAnimebyTitle(AnimeEpisode episode) throws IOException, InterruptedException {
//        System.setProperty("webdriver.chrome.driver", "/home/ubuntu/drivers/chromedriver");

        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");

        String urlPageAnime = episode.getUrlAnimeDescrtiption();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);
        driver.get(urlPageAnime);
        Document doc = Jsoup.connect(urlPageAnime).get();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.Description p")));

        String title = doc.select(".Container h1").text();
        String synopsis = doc.select("div.Description p").text();
        String thumbnail = BASE_URL + doc.select("div.AnimeCover img").attr("src");
        String stateText = doc.select("span.fa-tv").text();
        System.out.println("------------------");
        System.out.println(stateText);
        System.out.println("------------------");
        Boolean state = stateText.equalsIgnoreCase("FINALIZADO");
        Float rate = Float.parseFloat(doc.select("span.vtprmd").text());

        WebElement episodeContainer = driver.findElement(By.cssSelector("ul#episodeList"));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        int scrollHeight = 0;
        int lastScrollHeight = -1;

        while (scrollHeight != lastScrollHeight) {
            lastScrollHeight = scrollHeight;
            js.executeScript("arguments[0].scrollBy(0, 1000);", episodeContainer);
            Thread.sleep(500);
            scrollHeight = ((Number) js.executeScript("return arguments[0].scrollHeight;", episodeContainer)).intValue();
        }

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".ListCaps li")));

        List<WebElement> episodeElements = episodeContainer.findElements(By.cssSelector("li.fa-play-circle"));
        List<AnimeEpisode> episodes = new ArrayList<>();

        for (WebElement episodeElement : episodeElements) {
            try {
                String cap = episodeElement.findElement(By.cssSelector("p")).getText();
                String episodeUrl = episodeElement.findElement(By.cssSelector("a")).getAttribute("href");
                String thumbnailEpisode = episodeElement.findElement(By.cssSelector("img")).getAttribute("data-src");

                episodes.add(new AnimeEpisode(title, cap, thumbnailEpisode, episodeUrl,episode.getUrlAnimeDescrtiption()));
            } catch (NoSuchElementException e) {
                System.err.println("Error al extraer datos del episodio: " + e.getMessage());
            }
        }

        Anime anime = new Anime(title, synopsis, episodes, thumbnail, state, rate);
        driver.quit();
        return anime;
    }
}
