package com.example.demo.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class CountryResponse {

    public Name name;

    @Getter
    @Setter
    public static class Name{
//        @JsonProperty("official") // tells that comming json name is given
        private String official;
        private String common;

        public String toString(){
            return "common: " + common + " official: " + official;
        }
    }
}



