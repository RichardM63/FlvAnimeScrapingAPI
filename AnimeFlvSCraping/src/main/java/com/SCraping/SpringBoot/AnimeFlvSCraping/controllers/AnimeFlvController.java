package com.SCraping.SpringBoot.AnimeFlvSCraping.controllers;

import com.SCraping.SpringBoot.AnimeFlvSCraping.entity.AnimeEpisode;
import com.SCraping.SpringBoot.AnimeFlvSCraping.services.AnimeFlvServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/anime")
public class AnimeFlvController {
    @Autowired
    private AnimeFlvServiceImpl animeFlvService;

    @GetMapping("/latest")
    public ResponseEntity<?> getLatestEpisodes() throws IOException {
        Map<String, Object> response = new HashMap<>();
        try {
            List<AnimeEpisode> episodes = animeFlvService.getLatestEpisodes();
            response.put("mensaje","Los capitulos se han obtenido con exito.");
            response.put("episodios",episodes);
        }catch (Exception e){
            response.put("mensaje","No se ha logrado acceder al catalogo de capitulos.");
            response.put("error",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/episode/{title}")
    public ResponseEntity<?> getEpisodeData(@PathVariable String title){
        Map<String, Object> response = new HashMap<>();
        try {
            AnimeEpisode episode = animeFlvService.getEpisodeData(title);
            response.put("mensaje","Es posible acceder al capitulo con exito");
            response.put("episodio",episode);
        }catch (Exception e){
            response.put("mensaje","No se ha logrado acceder a los detalles del capitulo");
            response.put("error",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
