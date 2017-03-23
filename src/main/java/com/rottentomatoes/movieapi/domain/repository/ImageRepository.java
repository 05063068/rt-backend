package com.rottentomatoes.movieapi.domain.repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.flixster.image.ImageType;
import com.rottentomatoes.movieapi.domain.clients.ems.EmsClient;
import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.model.Image;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;

@Component
public class ImageRepository extends AbstractRepository implements ResourceRepository<Image, String> {

    private static Set<String> VALID_IMG_TYPES = new HashSet<>(Arrays.asList(new String[]{ImageType.MULTIUSE.getCode(), ImageType.RTMOVIE.getCode()}));

    @Override
    public <S extends Image> S save(S entity) {
        return null;
    }

    @Override
    public void delete(String id) {}

    @Override
    public Image findOne(String id, RequestParams requestParams) {
        // Validate imageId
        String[] idParts = id.split("-");
        String imageType;
        String imageId;
        String imageTable;

        if (idParts.length != 2) {
            return null;
        } else if (!VALID_IMG_TYPES.contains(idParts[0])) {
            return null;
        } else {
            imageType = idParts[0];
            imageId = idParts[1];
            imageTable = getImageTableForType(imageType);
        }
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("type", imageType);
        selectParams.put("imageTable", imageTable);

        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        Image image = (Image) emsClient.callEmsEntity(selectParams, "image", imageId, Image.class);
        return image;
    }

    private String getImageTableForType(String imageType) {
        if (imageType.equals(ImageType.MULTIUSE.getCode())) {
            return "multiuse_img";
        } else if (imageType.equals(ImageType.RTMOVIE.getCode())) {
            return "rtmovie_img";
        } else return null;
    }

    @Override
    public Iterable<Image> findAll(RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterable<Image> findAll(Iterable<String> ids, RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }

}
