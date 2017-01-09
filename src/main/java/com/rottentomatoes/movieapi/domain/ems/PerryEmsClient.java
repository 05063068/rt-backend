package com.rottentomatoes.movieapi.domain.ems;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.ems.EmsClient.DefaultEmsNamingStrategy;
import com.rottentomatoes.movieapi.domain.ems.EmsClient.JsonDecoder;
import com.rottentomatoes.movieapi.domain.ems.PreEmsClient.PreEmsNamingStrategy;
import com.rottentomatoes.movieapi.domain.meta.RootMetaDataInformation;
import com.rottentomatoes.movieapi.domain.model.Affiliate;
import com.rottentomatoes.movieapi.domain.model.Movie;
import com.rottentomatoes.movieapi.domain.model.ems.EmsModel;
import com.rottentomatoes.movieapi.domain.model.ems.EmsAffiliateModel;
import com.rottentomatoes.movieapi.domain.model.ems.EmsMovieModel;
import com.rottentomatoes.movieapi.domain.repository.AffiliateRepository;
import com.rottentomatoes.movieapi.domain.repository.EmsRouter;
import com.rottentomatoes.movieapi.domain.repository.movie.MovieListToMovieRepository;
import com.rottentomatoes.movieapi.domain.repository.movie.MovieRepository;
import com.rottentomatoes.movieapi.domain.repository.movie.MovieToAffiliatesRepository;


public class PerryEmsClient<T> extends EmsClient<T> {
    Class repository;
    
    private static final HashMap<Class,Class> modelToEMSClass = new HashMap<>();
    
    static {
        modelToEMSClass.put(Affiliate.class, EmsAffiliateModel.class);
        modelToEMSClass.put(MovieToAffiliatesRepository.class, EmsAffiliateModel.class);
        modelToEMSClass.put(Movie.class, EmsMovieModel.class);
        modelToEMSClass.put(MovieRepository.class, EmsMovieModel.class);
        modelToEMSClass.put(MovieListToMovieRepository.class, EmsMovieModel.class);
    }

    public PerryEmsClient(EmsRouter config, String hostUrl, Class repository) {
        super(config, hostUrl);
        this.repository = repository;
    }

    @Override
    public String getPathBase(String proposedPathBase) {
        if (repository.equals(MovieToAffiliatesRepository.class)) {
            return "/v1/ovd/movie/rt";
        } else if (repository.equals(AffiliateRepository.class)) {
            return "/v1/ovd/movie/rt";  // simulate endpoint, because it is fetching details already returned
        } else if ("all-box-office".equals(proposedPathBase)) {
            return "/v1/boxoffice";
        }
        return proposedPathBase;
    }

    @Override
    public String getIdString(String prefix, String id, String suffix) {
        return id;
    }

    @Override
    protected String constructUrl(String pathBase, String id) {
        return this.hostUrl + "/" + pathBase + "/" + id;
    }

    @Override
    protected JsonDecoder constructJsonEntityDecoder(Class c) {
        return new JsonDecoder() {
            public T doDecode(URL url) throws JsonParseException, JsonMappingException, IOException {
                EmsModel m = (EmsModel) objectMapper.readValue(url, modelToEMSClass.get(c));
                return (T) m.convert(m);
            }
        };
    }

    @Override
    protected JsonDecoder constructJsonListDecoder(CollectionType collectionType) {
        return new JsonDecoder() {
            public T doDecode(URL url) throws JsonParseException, JsonMappingException, IOException {
                JavaType keyType = collectionType.getContentType();
                Class cfClass = keyType.getRawClass();
                // in some cases, cfType will already be an emsXYZmodel, just use cfClass in that case
                Class modelClass = (modelToEMSClass.get(cfClass) == null)? cfClass : modelToEMSClass.get(cfClass);
                CollectionType c = TypeFactory.defaultInstance().constructCollectionType(List.class,  modelClass);
                List<EmsModel> ml = objectMapper.readValue(url, c);
                if (ml.size() > 0) {
                    return (T) ml.get(0).convertCollection(ml);
                }
                return (T) ml;
            }
        };
    }

    @Override
    public T callEmsEntity(Map<String, Object> selectParams, String pathBase, String id, Class c) {
        if (c.equals(RootMetaDataInformation.class)) {
            // EMS doesn't support count queries.  So, get the underlying list (probably cached) and return its size
            Class modelClass = modelToEMSClass.get(repository);
            List all_skus = (List)this.callEmsList(selectParams, pathBase, 
                    id, TypeFactory.defaultInstance().constructCollectionType(List.class,  modelClass));
            RootMetaDataInformation metaData = new RootMetaDataInformation();
            metaData.setTotalCount(all_skus.size());
            return (T) metaData;
        }
        return super.callEmsEntity(selectParams, pathBase, id, c);
    }

    @Override
    protected PropertyNamingStrategy getNamingStrategy() {
        return new PerryEmsNamingStrategy();
    }

    protected class PerryEmsNamingStrategy extends DefaultEmsNamingStrategy {

        private static final String PERRY_EMS_NAMING_CONFIG = "";

        private HashMap<String, String> namingMap;

        public PerryEmsNamingStrategy() {
            namingMap = new HashMap<>();

            // Field names that need to be translated
            namingMap.put("EmsAffiliateHostModel.id", "hostId");
            namingMap.put("EmsMovieRtInfoModel.rtId", "rt-movie-id");
            namingMap.put("EmsMovieFeaturesModel.rtInfo", "rt_info");
        }

        @Override
        public String translateName(String modelName, String fieldName)
        {
            String key = modelName + "." + fieldName;
            if (namingMap.containsKey(key)) {
                return namingMap.get(key);
            }

            String name = translateName(PERRY_EMS_NAMING_CONFIG, modelName, fieldName);
            return (name != null ? name : super.translateName(modelName, fieldName));
        }
    }
}
