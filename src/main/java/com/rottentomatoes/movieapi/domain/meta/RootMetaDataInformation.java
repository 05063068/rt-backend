package com.rottentomatoes.movieapi.domain.meta;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.response.MetaInformation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RootMetaDataInformation implements MetaInformation {

    RequestParams requestParams;

    public RootMetaDataInformation(RequestParams requestParams) {
        this.requestParams = requestParams;
    }
}
