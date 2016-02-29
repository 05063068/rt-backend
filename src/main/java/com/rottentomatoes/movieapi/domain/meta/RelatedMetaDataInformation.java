package com.rottentomatoes.movieapi.domain.meta;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.response.MetaInformation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelatedMetaDataInformation implements MetaInformation {
    RequestParams requestParams;
    public int totalCount;
}
