# Catalog API/RT Backend

## V1.1 Release Notes
* Refactored movie object with separated out supplementary info. The baseline movie object is now lighter weight (and faster). Additional information is delivered through the movieSupplementaryInfo relationship.
* Generic window tagging. Release windows are provided as an array of free-form tags (instead of separate 'theaterical' and 'dvd' categories)
* Rudimentary TV data support.
* Name change from cummulative_boxoffice to cumulative_boxoffice
* MovieCast now presented as MoviePersonnel, which has the 5 personnel types broken out separately. Actors can be limited with the special-case 'actorsLimit' filter.
* EMS-349 Built in expander (&expand=true)
* Publication and Critic now a full-featured endpoints that can by retrieved directly
* CriticSupplementaryInfo, Critic reviews and Critic affiliated Publications available as relationships of the `critic` object
* Critics list (at the /critic endpoint), supports initial `?filter={"initial":"p"}` and status `?filter={"status":"current"}` filtering. (status can be `current` or `legacy`)  
* Publication and Critic lists can be retrieved by accessing respective object endpoints without an id (`/critic`, `/publication`)
* Publication and Critic lists can be filtered alphabetically `?filter={"initial":"p"}`
* Critic has relationship of affiliated Publications (and vice versa)
* Full counts Publication and Critic reviews retrievable via meta object
* Quotes retrieved from dbtalk `/movie/9?include=["quotes"]`
* Critic groups `/criticGroup`, `/criticGroup/18?include=["critics"]`
* Person (e.g. actors) have PersonSupplementaryInfo and also Filmography via movieFilmography. (TV not implemented yet)
* Latest theatrical, dvd and quick reviews can be retrieved like so `/review?filter={"category":"dvd"}` (other categories are `theatrical` and `quick`)

## Setup and Build Notes

### Configuring JRE
catalog-api requires JRE1.8. If you have multiple JRE's installed you'll need to configure your IDE (Eclipse/IDEA) to use the right one.

### Custom Katharsis build
This project requires a custom build of katharsis-core and katharsis-servlet (1.0.x-FLIXSTER). I'm in conversation with the maintainers to do an official PR but until then the pom is wired up by default to use the pre-built custom repo at `https://raw.githubusercontent.com/flixster/mvn-repo/master`. No action is necessary to get the build going.

### Set up DB tunnel to RDS DB
Account on jump server required (jump.aws.prod.flixster.com)
    
Edit your `~/.ssh/config`

```    
Host jump
  User peterl 
  HostName jump.aws.prod.flixster.com
  LocalForward 10001 rdssharedsnap01.aws.prod.flixster.com:3306
``` 
After setting this up, establish your tunnel by connecting to the jump server `ssh jump`


### Configuring your profile

Spring supports convention-driven profiles. When the `spring.profiles.active` environment variable is set, the corresponding application.properties file (application-prod.properties in the case of `spring.profiles.active=prod`) will be read. 
This is the mechanism used to customize environment-based properties e.g. DB hostname.

The project will run with the 'prod' profile by default. To run locally, the 'dev' profile can be activated
by configuring the following JVM param
``` 
   -Dspring.profiles.active=dev  
```
If configuring via Eclipse interface, omit the -D.

### How to run with Maven
``` 
    $ mvn clean compile
    $ mvn spring-boot:run
```

### How to test with curl
```
    $ curl -v http://localhost:8080/movie/9

```

### Setting up AWS Elastic Beanstalk deployments
You can deploy automatically to Elastic Beanstalk using the beanstalker plugin.

Find your .m2 directory (usually in ~/.m2 e.g. C:\Users\Peter\.m2) and add/edit your settings.xml
```
<settings>
  <servers>
     ... other settings e.g. Github ...
    <server>
      <id>aws.amazon.com</id>
      <username>[Your personal AWS access key]</username>
      <password>[Your personal AWS access token]</password>
    </server>
  </servers>
</settings>
``` 

### Deploying to the Elastic Beanstalk Environment
The project can be deployed to Elastic Beanstalk Environment simply by executing the following command:
```
mvn -Dbeanstalker.env.ref=<env_name_here> beanstalk:upload-source-bundle beanstalk:create-application-version beanstalk:update-environment
```

# Example Requests

## JSON-API and Katharsis concepts
JSON-API is one particular flavor of a HATEOAS.  http://jsonapi.org/

Katharsis is the prevalent framework for Java implementing this standard. http://katharsis.io/

The main advantage of JSON-API is a well defined mechanism for including resources by reference, without duplication.

  
## Relationships vs Resource Inclusion
In this section I want to clarify the distinction between the inclusion of relationships and resources.

### No relationships or resources included
A minimal Katharsis response looks like this
```
{
   data: {
      type: "movie",
      id: "9",
      attributes: {
         studio: "20th Century Fox",
         year: 2005,
         title: "Star Wars: Episode III - Revenge of the Sith 3D",
      },
      relationships: {
         movieCast: {
            links: {
               self: "http://localhost:8080/movie/9/relationships/movieCast",
               related: "http://localhost:8080/movie/9/movieCast"
            }
         }
      },
      links: {
         self: "http://localhost:8080/movie/9"
      }
   },
   included: [ ]
}
```
Note that the list of cast memebers (part of the `relationships` object) is provided not immediately, but only as a reference. This is the default behavior when the lazy annotation is used
```
@JsonApiToMany(lazy=true)
protected Iterable<MovieCast> movieCast;
```
and no `include` URL param is applied to the request.
```
http://localhost:8080/movie/9
```

This response can be composed efficiently with one single SQL call to the `movie` table. It is useful for isntances where we only need basic data about an object. 

### Relationships provided but related resources NOT included
Without the `lazy` attribute on the `@JsonApiToMany` annotation, we would get this result:

```
{
   data: {
      type: "movie",
      id: "9",
      attributes: {
         studio: "20th Century Fox",
         year: 2005,
         title: "Star Wars: Episode III - Revenge of the Sith 3D",
      },
      relationships: {
        movieCast: {
          links: {
            self: "http://localhost:8080/movie/9/relationships/movieCast",
            related: "http://localhost:8080/movie/9/movieCast"
          },
          data: [
            {
              type: "movieCast",
              id: "551936484"
            },
            {
              type: "movieCast",
              id: "112312412"
            },
            ...etc.
          ]
        }
      },
      links: {
         self: "http://localhost:8080/movie/9"
      }
   },
   included: [ ]
}
```
In this example, the `relationships` object is populated with a `data` array consisting of the `type` and `id` of the referenced `movieCast` objects. However note that the `included` array remains empty. A response in this form is not particularly useful because it is unlikely that one would use the `type` and `id` fields alone. We would need to retrieve each `movieCast` to access the missing information and this triggers the *n+1* problem. Furthermore, the way myBatis works means that in order to construct data array we're retrieving (and throwing away) the rest of the fields in `movieCast`.

### Relationships provided and related resources included
A response in this form can be obtained by making the following request

```
http://localhost:8080/movie/9?include=["movieCast"]
```

In this example, the relationship is provided (`relationships->movieCast->data` is populated). The `movieCast` object ID's can in turn be dereferenced by iterating through the `include` array.

This response is self contained and captures all its relationship data internally. The response can be fulfilled in 2 SQL queries - one for the movie itself and one for the `movieCast` objects.



```
{
   data: {
      type: "movie",
      id: "9",
      attributes: {
         studio: "20th Century Fox",
         year: 2005,
         title: "Star Wars: Episode III - Revenge of the Sith 3D",
      },
      relationships: {
        movieCast: {
          links: {
            self: "http://localhost:8080/movie/9/relationships/movieCast",
            related: "http://localhost:8080/movie/9/movieCast"
          },
          data: [
            {
              type: "movieCast",
              id: "551936484"
            },
            {
              type: "movieCast",
              id: "112312412"
            },
            ...etc.
          ]
        }
      },
      links: {
         self: "http://localhost:8080/movie/9"
      }
  },
  included: [
    {
      type: "movieCast",
      id: "551936484",
      attributes: {
        characters: [ "Darth Vader" ],
        role: "ACT"
      },
      relationships: {},
      links: {
        self: "http://localhost:8080/movieCast/771360177"
      }
    }
  ]
}
```


