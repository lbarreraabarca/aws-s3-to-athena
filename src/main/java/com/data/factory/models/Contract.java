package com.data.factory.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Contract {

    @JsonProperty private String path;
    @JsonProperty private String bucketRegion;
    @JsonProperty private String localPath;

}
