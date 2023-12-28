package com.artisdetail.api.artistdetail.api.service;

import com.artisdetail.api.artistdetail.api.Client.ArtistClient;
import com.artisdetail.api.artistdetail.api.Client.CoverArtArchiveClient;
import com.artisdetail.api.artistdetail.api.model.MBID;
import com.artisdetail.api.artistdetail.api.model.down.ArtistDto;
import com.artisdetail.api.artistdetail.api.model.down.ReleaseGroups;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;



public class ArtistServiceTest {

    ArtistClient<ArtistDto> mocArtistClient;
    CoverArtArchiveClient moCoverArtArchiveClient;
    private  final MBID mbid= MBID.from("123xx-456yyy--789zzz");
    @BeforeEach
    public void beforeEach() {
        mocArtistClient = mock(ArtistClient.class);
        moCoverArtArchiveClient =mock(CoverArtArchiveClient.class);
        }

  @Test
    public void test_client(){
       doReturn(Mono.just(getArtistDto())).when(mocArtistClient).getAs(any(), any());
        ArtistService artistService= new ArtistService(mocArtistClient,moCoverArtArchiveClient );
        StepVerifier.create(artistService.getArtistDetail(mbid))
                .expectNextMatches(obj ->
                    !obj.getName().isEmpty() &&
                     obj.getId().equals(mbid.getMbid())
                ).verifyComplete();
    }

    @Test
     public void test_null_arguments_artist_client() {
        StepVerifier.create(new ArtistService(mocArtistClient,moCoverArtArchiveClient).getArtistDetail(null))
                .verifyComplete();
    }


    private ArtistDto getArtistDto(){
        List<ReleaseGroups> releaseGroups= Arrays.asList(new ReleaseGroups());
        return new  ArtistDto(mbid.getMbid(), "name","gender","country","disambiguation",releaseGroups);
    }

}
