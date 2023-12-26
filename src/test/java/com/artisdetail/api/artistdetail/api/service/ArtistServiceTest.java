package com.artisdetail.api.artistdetail.api.service;

import com.artisdetail.api.artistdetail.api.Client.ArtistClient;
import com.artisdetail.api.artistdetail.api.model.MBID;
import com.artisdetail.api.artistdetail.api.model.down.ArtistDto;
import com.artisdetail.api.artistdetail.api.model.up.Artist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;



public class ArtistServiceTest {

    ArtistClient mocArtistClient;
    private  Mono<Artist> artistMono;
    private  final MBID mbid= MBID.from("123xx-456yyy--789zzz");
    @BeforeEach
    public void beforeEach() {
        mocArtistClient = mock(ArtistClient.class);
        }
  @Test
    public void test_client(){
       doReturn(Mono.just(getArtistDto())).when(mocArtistClient).getAs(any(), any());
        ArtistService artistService= new ArtistService(mocArtistClient);
        StepVerifier.create(artistService.getArtistDetail(mbid))
                .expectNextMatches(obj ->
                    !obj.getName().isEmpty() &&
                     obj.getId().equals(mbid.getMbid())
                ).verifyComplete();
    }

    @Test
     public void test_null_arguments_artist_client() {
        StepVerifier.create(new ArtistService(mocArtistClient).getArtistDetail(null))
                .verifyComplete();
    }

    private ArtistDto getArtistDto(){
        return new  ArtistDto(mbid.getMbid(), "name","gender","country","disambiguation");
    }

}
