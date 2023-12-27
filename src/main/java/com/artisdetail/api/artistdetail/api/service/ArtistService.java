package com.artisdetail.api.artistdetail.api.service;

import com.artisdetail.api.artistdetail.api.Client.ArtistClient;
import com.artisdetail.api.artistdetail.api.Client.CoverArtArchiveClient;
import com.artisdetail.api.artistdetail.api.model.down.Album;
import com.artisdetail.api.artistdetail.api.model.down.CovertArtArchive;
import com.artisdetail.api.artistdetail.api.model.down.ReleaseGroups;
import com.artisdetail.api.artistdetail.api.model.up.Artist;
import com.artisdetail.api.artistdetail.api.model.down.ArtistDto;
import com.artisdetail.api.artistdetail.api.model.MBID;
import com.artisdetail.api.artistdetail.api.model.up.ArtistDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class ArtistService {


    private ArtistClient<ArtistDto> artistClient;

    private static CoverArtArchiveClient coverArtClient;

    @Autowired
    public ArtistService(ArtistClient<ArtistDto> artistClient, CoverArtArchiveClient coverArtClient) {
        this.artistClient = Objects.requireNonNull(artistClient);
        this.coverArtClient = Objects.requireNonNull(coverArtClient);
    }


    public Mono<ArtistDetail> getArtistDetail(MBID id) {
        log.debug("artist detail request by id: {}", id);
        if (id == null) return Mono.empty();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/ws/2/artist/").append(id.getMbid()).append("?&fmt=json&inc=url-rels+release-groups");

        Mono<ArtistDto> artistInfo = artistClient.getAs(stringBuilder.toString(), new ParameterizedTypeReference<>() {
        });
        Mono<List<ReleaseGroups>> rgList = artistInfo.map(rg -> rg.getReleaseGroups().stream().collect(Collectors.toList()));
        Mono<List<Album>> listOFCoverArt = getAlbum(rgList);
        return Mono.zip(artistInfo.map(Artist::from), listOFCoverArt).map(w ->ArtistDetail.from(w.getT1(),w.getT2()));
    }



    private static Mono<List<Album>> getAlbum(Mono<List<ReleaseGroups>> releaseGroups) {

        return releaseGroups.flatMapMany(Flux::fromIterable).map(c -> {
            return fetchDataForId(c.getId()).map(b -> b.getImages().stream().collect(Collectors.toList()).get(0).getImage())
                    .map(r -> {
                        return new Album(c.getId(), c.getTitle(), r);
                    });
        }).flatMap(mono -> mono).collectList();

    }

    private static Mono<CovertArtArchive> fetchDataForId(String id) {
        log.info("id  : {}", id);
        if (id == null) return Mono.empty();
        return coverArtClient.getAs("/release-group/" + id + "?fmt=json", new ParameterizedTypeReference<CovertArtArchive>() {
        });
    }

}
