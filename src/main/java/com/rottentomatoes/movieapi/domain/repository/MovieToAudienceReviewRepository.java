package com.rottentomatoes.movieapi.domain.repository;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.meta.RelatedMetaDataInformation;
import com.rottentomatoes.movieapi.domain.model.AudienceReview;
import com.rottentomatoes.movieapi.domain.model.Movie;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.MetaRepository;
import io.katharsis.repository.RelationshipRepository;
import io.katharsis.response.MetaDataEnabledList;
import io.katharsis.response.MetaInformation;

@SuppressWarnings("rawtypes")
@Component
public class MovieToAudienceReviewRepository extends AbstractRepository implements RelationshipRepository<Movie, String, AudienceReview, String>, MetaRepository {


    @Override
    public void addRelations(Movie arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void removeRelations(Movie arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void setRelation(Movie arg0, String arg1, String arg2) {
    }

    @Override
    public void setRelations(Movie arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public AudienceReview findOneTarget(String sourceId, String fieldName, RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MetaDataEnabledList<AudienceReview> findManyTargets(String movieId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        List<AudienceReview> list = new ArrayList<>();
        MetaDataEnabledList<AudienceReview> mList = new MetaDataEnabledList<>(list);

        // TODO: Replace with actual myBatis mapper implementation        
        AudienceReview dummy1 = new AudienceReview();
        AudienceReview dummy2 = new AudienceReview();
        AudienceReview dummy3 = new AudienceReview();
        
        Map<String, Object> dummy1UserImage = new HashMap<>();
        dummy1UserImage.put("thumbnailUrl", "http://graph.facebook.com/v2.2/122604120/picture");
        
        dummy1.setId("1");
        dummy1.setMovieId(9L);
        dummy1.setCreationDate(ZonedDateTime.parse("2007-12-03T10:15:30+01:00[US/Pacific]"));
        dummy1.setRatingDate(ZonedDateTime.parse("2007-12-03T10:15:30+01:00[US/Pacific]"));
        dummy1.setScore(4.5);
        dummy1.setTopReviewer(true);
        dummy1.setComment("This is a cool movie!");
        dummy1.setUserId(100001L);
        dummy1.setUserName("Bob Bobson");
        dummy1.setUserImage(dummy1UserImage);
        
        Map<String, Object> dummy2UserImage = new HashMap<>();
        dummy2UserImage.put("thumbnailUrl", "http://graph.facebook.com/v2.2/122604120/picture");

        dummy2.setId("2");
        dummy2.setMovieId(9L);
        dummy2.setCreationDate(ZonedDateTime.parse("2007-12-03T10:15:30+01:00[US/Pacific]"));
        dummy2.setRatingDate(ZonedDateTime.parse("2007-12-03T10:15:30+01:00[US/Pacific]"));
        dummy2.setScore(4.0);
        dummy2.setTopReviewer(true);
        dummy2.setComment("Stephen Curry’s absence reduced the “wow” factor Monday night at Oracle Arena, but it didn’t prevent the Warriors from seizing a two games-to-none lead over Houston in their first-round playoff series.");
        dummy2.setUserId(100002L);
        dummy2.setUserName("Bob Boberson");
        dummy2.setUserImage(dummy2UserImage);

        Map<String, Object> dummy3UserImage = new HashMap<>();
        dummy3UserImage.put("thumbnailUrl", "http://graph.facebook.com/v2.2/122604120/picture");
       
        dummy3.setId("3");
        dummy3.setMovieId(9L);
        dummy3.setCreationDate(ZonedDateTime.parse("2007-12-03T10:15:30+01:00[US/Pacific]"));
        dummy3.setRatingDate(ZonedDateTime.parse("2007-12-03T10:15:30+01:00[US/Pacific]"));
        dummy3.setScore(3.0);
        dummy3.setTopReviewer(true);
        dummy3.setComment("As more than a foot of rain deluged the nation's fourth-largest city, inundating homes, shutting down major highways and leaving at least four people dead, Houston's mayor said there was no immediate solution.");
        dummy3.setUserId(100003L);
        dummy3.setUserName("Bob McBoberson");
        dummy3.setUserImage(dummy3UserImage);
        
        list.add(dummy1);
        list.add(dummy2);
        list.add(dummy3);        
        
        return mList;
    }

    @Override
    public MetaInformation getMetaInformation(Object root, Iterable resources, RequestParams requestParams, Serializable castedResourceId) {
        Map<String, Object> selectParams = new HashMap<>();
        RelatedMetaDataInformation metaData = new RelatedMetaDataInformation();
        metaData.setTotalCount(3);
        
        if (root instanceof RelationshipRepository) {
            metaData.setRequestParams(requestParams);
        }
        return metaData;
    }
}
