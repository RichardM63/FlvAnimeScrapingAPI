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
public class AnimeFlvRespositoryImpl implements IAnimeFlvRepository {

    private static final String BASE_URL = "https://www3.animeflv.net";
    private static List<AnimeEpisode> episodes;


    public AnimeFlvRespositoryImpl() throws IOException {
        episodes = new ArrayList<>();
        Document doc = Jsoup.connect(BASE_URL).get();
        Elements elements = doc.select(".ListEpisodios li");

        for (Element element : elements) {
            String title = element.select(".Title").text();
            String cap = element.select(".Capi").text();
            String episodeUrl = BASE_URL + element.select("a").attr("href");
            String thumbnail = BASE_URL + element.select("img").attr("src");

            episodes.add(new AnimeEpisode(title, cap, thumbnail, episodeUrl));
        }
    }

    @Override
    public List<AnimeEpisode> getLatestEpisodes() {
        return episodes;
    }

    @Override
    public AnimeEpisode getEpisodeData(AnimeEpisode animeEpisode) {
//        System.setProperty("webdriver.chrome.driver", "/home/ubuntu/drivers/chromedriver");

        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");

        ChromeOptions optionsChrome = new ChromeOptions();
        optionsChrome.addArguments("--headless");
        optionsChrome.addArguments("--no-sandbox");
        optionsChrome.addArguments("--disable-dev-shm-usage");
        optionsChrome.addArguments("--remote-debugging-port=9222");

        WebDriver driver = new ChromeDriver(optionsChrome);
        String urlPageAnime = animeEpisode.getUrlPaginaCapitulo();

        System.out.println(urlPageAnime);
        try {

            driver.get(urlPageAnime);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            List<WebElement> optionElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("ul.CapiTnv li")));

            List<WebElement> links = driver.findElements(By.cssSelector(".CapNv a"));
            List<String> hrefs = new ArrayList<>();
            String urlAnimeDescrtiption="";
            String nextEpisode="";
            String lastEpisode="";
            for (WebElement link : links) {
                lastEpisode = link.getText().equals("ANTERIOR") ? link.getAttribute("href"):lastEpisode;
                urlAnimeDescrtiption = link.getText().equals("") ? link.getAttribute("href"):urlAnimeDescrtiption;
                nextEpisode = link.getText().equals("SIGUIENTE") ? link.getAttribute("href"):nextEpisode;
            }

            Map<String, String> options = new HashMap<>();

            for (WebElement option : optionElements) {
                String optionTitle = option.getAttribute("title");
                if (!optionTitle.equals("Netu")) {
                    option.click();
                    WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("iframe")));
                    if (optionTitle != null && !optionTitle.isEmpty()) {
                        options.put(optionTitle, iframe.getAttribute("src"));
                    } else {
                        System.out.println("Opcion invalid");
                    }
                }
            }
            animeEpisode.setCap(driver.findElement(By.cssSelector(".CapiTop h2")).getText());
            animeEpisode.setOptions(options);
            animeEpisode.setNextEpisode(nextEpisode);
            animeEpisode.setUrlAnimeDescrtiption(urlAnimeDescrtiption);
            animeEpisode.setLastEpisode(lastEpisode);
            return animeEpisode;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            driver.quit();
        }
    }
}
