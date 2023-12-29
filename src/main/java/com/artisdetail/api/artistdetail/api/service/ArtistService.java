package com.artisdetail.api.artistdetail.api.service;

import com.artisdetail.api.artistdetail.api.Client.ArtistClient;
import com.artisdetail.api.artistdetail.api.Client.CoverArtArchiveClient;
import com.artisdetail.api.artistdetail.api.model.down.Album;
import com.artisdetail.api.artistdetail.api.model.down.CoverArchiveDto;
import com.artisdetail.api.artistdetail.api.model.down.ReleaseGroups;
import com.artisdetail.api.artistdetail.api.model.down.ArtistDto;
import com.artisdetail.api.artistdetail.api.model.MBID;
import com.artisdetail.api.artistdetail.api.model.up.ArtistDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArtistService {


    private  ArtistClient artistClient;

    private  CoverArtArchiveClient coverArtClient;

    @Autowired
    public ArtistService(ArtistClient artistClient, CoverArtArchiveClient coverArtClient) {
        this.artistClient = Objects.requireNonNull(artistClient);
        this.coverArtClient = Objects.requireNonNull(coverArtClient);
    }


    public Mono<ArtistDetail> getArtistDetail(MBID id) {
        log.debug("artist detail request by id: {}", id);
        if (id == null) return Mono.empty();
        Mono<ArtistDto> artistInfo = getArtistData(id.getMbid());
        Mono<List<ReleaseGroups>> rgList = artistInfo.map(rg -> new ArrayList<>(rg.getReleaseGroups()));
        Mono<List<Album>> listOFCoverArt = getAlbum(rgList);

        return Mono.zip(artistInfo, listOFCoverArt).map(t -> {
            return ArtistDetail.from(t.getT1(),t.getT2());
        });
    }

     private  Mono<ArtistDto> getArtistData(String id){
         StringBuilder stringBuilder = new StringBuilder();
         stringBuilder.append("/ws/2/artist/").append(id).append("?&fmt=json&inc=url-rels+release-groups");
         return artistClient.getAs(stringBuilder.toString(), new ParameterizedTypeReference<>() {
        });
     }


    private  Mono<List<Album>> getAlbum(Mono<List<ReleaseGroups>> releaseGroups) {
        return releaseGroups.flatMapMany(Flux::fromIterable).map(c -> {
            return getCoverArtArchive(c.getId()).map(b -> b.getImages().stream().collect(Collectors.toList()).get(0).getImage())
                    .map(r -> {
                        return new Album(c.getId(), c.getTitle(), r);
                    });
        }).flatMap(mono -> mono).collectList();

    }

    private  Mono<CoverArchiveDto> getCoverArtArchive(String id) {
        log.debug("id  : {}", id);
        if (id == null) return Mono.empty();
        return coverArtClient.getAs("/release-group/" + id + "?fmt=json", new ParameterizedTypeReference<CoverArchiveDto>() {
        });
    }

}
