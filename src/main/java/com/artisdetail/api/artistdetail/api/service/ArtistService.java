package com.artisdetail.api.artistdetail.api.service;

import com.artisdetail.api.artistdetail.api.Client.ArtistClient;
import com.artisdetail.api.artistdetail.api.model.up.Artist;
import com.artisdetail.api.artistdetail.api.model.down.ArtistDto;
import com.artisdetail.api.artistdetail.api.model.MBID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Slf4j
public class ArtistService {



    private ArtistClient<ArtistDto> artistClient;

    @Autowired
    public ArtistService(ArtistClient<ArtistDto> artistClient) {
        this.artistClient = Objects.requireNonNull(artistClient);
    }

    public Mono<Artist> getArtistDetail(MBID id) {
        log.debug("artist detail request by id: {}", id);
        if(id == null) return  Mono.empty();
         StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/ws/2/artist/").append(id.getMbid()).append("?&fmt=json&inc=url-rels+release-groups");

        return artistClient.getAs(stringBuilder.toString(), new ParameterizedTypeReference<>() {
        }).map(Artist::from);
    }

}
