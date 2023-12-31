package com.artisdetail.api.artistdetail.api.Exception;

import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.nio.charset.Charset;

public class CustomizingWebClientResponseException extends WebClientResponseException {


    private CustomizingWebClientResponseException(int statusCode, String statusText, HttpHeaders headers, byte[] body, Charset charset) {
        super(statusCode, statusText, headers, body, charset);
    }
    private CustomizingWebClientResponseException(String errorMsg){
        super(null,errorMsg, null, null, null,null);
    }

    public static CustomizingWebClientResponseException ArtistClientResponseException(int statusCude, String statusText) {
        return new CustomizingWebClientResponseException(statusCude, statusText, null, null, null);
    }

  public static CustomizingWebClientResponseException  CustomRetryFailedException(String errorMsg){
        return new CustomizingWebClientResponseException(errorMsg);
  }


}
