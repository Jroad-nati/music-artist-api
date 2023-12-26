package com.artisdetail.api.artistdetail.api.model.up;

import com.artisdetail.api.artistdetail.api.model.down.ArtistDto;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.assertEquals;


public class ArtistTest {
    @Test
    void testClassObjectMapping() {
        ArtistDto artistDto=new ArtistDto("id","name","gender", "country", "disambiguation");
        assertEquals("id", Artist.from(artistDto).getId());
    }
}
