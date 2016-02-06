package com.rottentomatoes.movieapi.domain.meta;

import io.katharsis.queryParams.PaginationKeys;
import io.katharsis.response.MetaInformation;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class RelatedMetaDataInformation implements MetaInformation {
    public int totalCount;
}
