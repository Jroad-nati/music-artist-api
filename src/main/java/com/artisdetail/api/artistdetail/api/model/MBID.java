package com.artisdetail.api.artistdetail.api.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MBID {
    private  String mbid;

    public static MBID from(String id){
        Objects.requireNonNull(id);
        return   new MBID(id);
    }
}
