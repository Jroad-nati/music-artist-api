package com.artisdetail.api.artistdetail.api.Controller;


import com.artisdetail.api.artistdetail.api.model.up.Artist;
import com.artisdetail.api.artistdetail.api.model.MBID;
import com.artisdetail.api.artistdetail.api.service.ArtistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RestController
@RequestMapping("/api/music/artist")
@Slf4j
public class ArtistController {

    private final ArtistService artistsService;

    @Autowired
    public ArtistController(final ArtistService service) {
        this.artistsService= Objects.requireNonNull(service);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/detail/{id}")
    public  Mono<Artist> getArtistDetail(@PathVariable String id ){
        log.info("Artist id : {}", id);
        return artistsService.getArtistDetail(MBID.from(id));     }
}


