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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

@Configuration
public class AnimeRepositoryImpl implements IAnimeRepositoy {

    private static final String BASE_URL = "https://www3.animeflv.net/";

    @Override
    public Anime getAnimebyUrl(AnimeEpisode episode) throws IOException, InterruptedException {
        System.setProperty("webdriver.chrome.driver", "/home/ubuntu/drivers/chromedriver");

//        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        ExecutorService executor = Executors.newFixedThreadPool(2);

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
        Boolean state = stateText.equalsIgnoreCase("FINALIZADO");
        Float rate = Float.parseFloat(doc.select("span.vtprmd").text());

        WebElement episodeContainer = driver.findElement(By.cssSelector("ul#episodeList"));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        int caps = Integer.parseInt(Objects.requireNonNull(driver.findElement(By.cssSelector("input.mseen")).getAttribute("data-number")));
        List<WebElement> episodeElements = episodeContainer.findElements(By.cssSelector("li.fa-play-circle"));
        AnimeEpisode[] episodes = new AnimeEpisode[caps+1];

        String cap="";
        String episodeUrl="";
        String thumbnailEpisode="";
        boolean alo=true;
        for (WebElement episodeElement : episodeElements) {
            try {
                if(alo){
                cap = episodeElement.findElement(By.cssSelector("p")).getText();
                episodeUrl = episodeElement.findElement(By.cssSelector("a")).getAttribute("href");
                thumbnailEpisode = episodeElement.findElement(By.cssSelector("img")).getAttribute("data-src");
                episodes[caps] =(new AnimeEpisode(title, cap, thumbnailEpisode, episodeUrl,episode.getUrlAnimeDescrtiption()));
                caps--;
                break;
                }
                } catch (NoSuchElementException e) {
                System.err.println("Error al extraer datos del episodio: " + e.getMessage());
            }
        }
        for(int i=0;i<caps;caps--) {
            cap = cap.replaceAll("\\d+$", String.valueOf(caps));
            episodeUrl = episodeUrl.replaceAll("\\d+$", String.valueOf(caps));
            episodes[caps] = (new AnimeEpisode(title, cap, thumbnailEpisode, episodeUrl, episode.getUrlAnimeDescrtiption()));
        }
        Anime anime = new Anime(title, synopsis, urlPageAnime,Arrays.asList(episodes) , thumbnail,state, rate);
        return anime;
    }

    @Override
    public List<Anime> searchByTitle(String title) throws IOException {
        String urlSerch = BASE_URL.concat("browse?q=").concat(title);
        String currentPageUrl = urlSerch;
        List<Anime> animeList = new ArrayList<>();
        do{
            urlSerch =currentPageUrl;
        Document doc = Jsoup.connect(urlSerch).get();
        Elements elements = doc.select(".ListAnimes li");
            doc = Jsoup.connect(currentPageUrl).get();
            Element nextPageElement = doc.select("ul.pagination li.active + li a").first();
            if (nextPageElement != null && !nextPageElement.attr("href").equals("#")) {
                currentPageUrl = BASE_URL + nextPageElement.attr("href");
            } else {
                currentPageUrl = null;
            }
            if(currentPageUrl==urlSerch){break;}
        for (Element element : elements) {
            String titleAnime = element.select("h3.Title").text();
            String synopsis = element.select(".Description p").eq(1).text();
            String rateText = element.select("span.Vts").text();
            Float rate = rateText.isEmpty() ? 0.0f : Float.parseFloat(rateText);
            String url = BASE_URL + element.select("a").attr("href");
            String thumbnail = BASE_URL + element.select("img").attr("src");

            animeList.add(new Anime(titleAnime,synopsis,url,thumbnail,rate));
        }

    } while (currentPageUrl != null);
        return animeList;
    }
}
