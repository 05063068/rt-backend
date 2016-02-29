package com.rottentomatoes.movieapi.domain.repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.model.Image;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;

@Component
public class ImageRepository extends AbstractRepository implements ResourceRepository<Image, String> {
    
    
    
    private static Set<String> VALID_IMG_TYPES = new HashSet<String>(Arrays.asList(new String[]{"movie_img"}));    
    @Override
    public <S extends Image> S save(S entity) {
        return null;
    }
        
    @Override
    public void delete(String id) {

    }

	@Override
	public Image findOne(String id, RequestParams requestParams) {
		// Validate imageId
		String[] idParts = id.split("-");
		String imageType;
		String imageId;
		if(idParts.length != 2){
			return null;
		}
		else if(!VALID_IMG_TYPES.contains(idParts[0])){
			return null;
		}
		else {
			imageType = idParts[0];
			imageId = idParts[1];
		}
		
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("type",imageType);
        selectParams.put("id", imageId);
        
        Image image = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.ImageMapper.selectImageByIdAndType", selectParams);
        return image;
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
