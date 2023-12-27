package com.artisdetail.api.artistdetail.api.model.up;

import com.artisdetail.api.artistdetail.api.model.down.Album;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class ArtistDetail {
    private Artist artist;
    private List<Album> albums;

   public static ArtistDetail from(Artist artist, List<Album> albums){
       return new ArtistDetail(artist,albums);
   }


}
