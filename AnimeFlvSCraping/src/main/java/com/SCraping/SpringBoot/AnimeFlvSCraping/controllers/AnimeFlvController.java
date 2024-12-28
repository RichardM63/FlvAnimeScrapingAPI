package com.SCraping.SpringBoot.AnimeFlvSCraping.controllers;

import com.SCraping.SpringBoot.AnimeFlvSCraping.entity.Anime;
import com.SCraping.SpringBoot.AnimeFlvSCraping.entity.AnimeEpisode;
import com.SCraping.SpringBoot.AnimeFlvSCraping.repositories.AnimeRepositoryImpl;
import com.SCraping.SpringBoot.AnimeFlvSCraping.services.AnimeFlvServiceImpl;
import com.SCraping.SpringBoot.AnimeFlvSCraping.services.AnimeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@CrossOrigin(origins = "http://localhost:3000",originPatterns = "*")
@RestController
@RequestMapping("/api/anime")
public class AnimeFlvController {
    @Autowired
    private AnimeFlvServiceImpl animeFlvService;
    @Autowired
    private AnimeServiceImpl animeService;

    @GetMapping("/description")
    public ResponseEntity<?> getanimeDescription(@RequestBody AnimeEpisode episode) throws IOException {
        Map<String, Object> response = new HashMap<>();
        try {
            Anime anime = animeService.getAnimebyTitle(episode);
            if(anime==null){
                response.put("mensaje","El anime ingresado no existe.");
                response.put("error",new Exception().getMessage());
                return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
            }
            response.put("mensaje","Es posible acceder a los detalles del anime con exito.");
            response.put("Anime",anime);
        }catch (Exception e){
            response.put("mensaje","No se ha logrado acceder a los detalles del anime.");
            response.put("error",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);


    }

    @GetMapping("/latest")
    public ResponseEntity<?> getLatestEpisodes() throws IOException {
        Map<String, Object> response = new HashMap<>();
        try {
            List<AnimeEpisode> episodes = animeFlvService.getLatestEpisodes();
            if(episodes==null || episodes.isEmpty()){
                response.put("mensaje","Los capitulos no se han encontrado.");
                response.put("error",new Exception().getMessage());
                return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
            }
            response.put("mensaje","Los capitulos se han obtenido con exito.");
            response.put("episodios",episodes);
        }catch (Exception e){
            response.put("mensaje","No se ha logrado acceder al catalogo de capitulos.");
            response.put("error",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/episode")
    public ResponseEntity<?> getEpisodeData(@RequestBody AnimeEpisode animeEpisode){
        Map<String, Object> response = new HashMap<>();
        try {
            AnimeEpisode episode = animeFlvService.getEpisodeData(animeEpisode);
            if(episode==null){
                response.put("mensaje","El capitulo ingresado no existe.");
                response.put("error",new Exception().getMessage());
                return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
            }
            response.put("mensaje","Es posible acceder al capitulo con exito.");
            response.put("episodio",episode);
        }catch (Exception e){
            response.put("mensaje","No se ha logrado acceder a los detalles del capitulo.");
            response.put("error",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
