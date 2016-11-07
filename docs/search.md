# Search Endpoints

## Searchable Objects
The following objects can be searched by applying `search` filter to the root object node (no ID).

### Movie
Example: `/movie?filters={"search":{"title":"titanic"}}`

### Person
Example: `/person?filter={"search":{"name":"bob"}}`

### Franchise
Example: `/franchise?filter={"search":{"title":"game"}}`

### Critic
Example: `/critic?filter={"index-search":{"name":"bob"}}`

Note: The Cloudsearch filter is `index-search`. A separate, simpler `search` filter is available that performs SQL `LIKE` based search.

### TV Series
Example: `/tvSeries?filter={"search":{"title":"game"}}`

## Search Filter Parameters
The search filter accepts a JSON object. Key-value pairs contained within
this object are passed directly through to the search microservice. See
Swagger documentation here:
http://search.aws.prod.flixster.com/documentation/index.html
