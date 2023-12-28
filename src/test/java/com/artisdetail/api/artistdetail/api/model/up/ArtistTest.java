package com.artisdetail.api.artistdetail.api.model.up;

import com.artisdetail.api.artistdetail.api.model.down.Album;
import com.artisdetail.api.artistdetail.api.model.down.ArtistDto;
import com.artisdetail.api.artistdetail.api.model.down.ReleaseGroups;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;



public class ArtistTest {
    @Test
    void testClassObjectMapping() {
        List<ReleaseGroups>  releaseGroups= new ArrayList<>();
        List<Album> albums=new ArrayList<>();
        ArtistDto artistDto=new ArtistDto("id","name","gender", "country", "disambiguation", releaseGroups);

        assertEquals("id", ArtistDetail.from(artistDto,albums).getId());
    }
}
