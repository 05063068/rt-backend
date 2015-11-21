package com.rottentomatoes.movieapi.mappers;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.utils.PropertyUtils;
import com.squareup.pollexor.Thumbor;
import com.squareup.pollexor.ThumborUrlBuilder;

import lombok.extern.log4j.Log4j;

@Log4j
@MappedJdbcTypes(JdbcType.INTEGER)

public class ImageUrlTypeHandler extends BaseTypeHandler<String> {

	
	private static final int THUMBNAIL_HEIGHT = 270;
	private static final int THUMBNAIL_WIDTH = 150;
	
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType)
			throws SQLException {
		// No-op : Read only implementation
	}

	@Override
	public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String imageId = rs.getString(columnName);
		return getImageUrl("movie", imageId, THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT);
	}

	@Override
	public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String imageId = rs.getString(columnIndex);
		return getImageUrl("movie", imageId, THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT);
	}

	@Override
	public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	// TODO: Implement more sophisticated resizing
    private String getImageUrl(String imageType, String imageId, int width, int height) {
    	

    	String RESIZER_HOST = PropertyUtils.getProperty("images.resizer.host");;
    	String RESIZER_KEY = PropertyUtils.getProperty("images.resizer.key");;
    	String ORIGIN_HOST = PropertyUtils.getProperty("images.origin.host");
    	Thumbor flxMainThumbor = Thumbor.create(RESIZER_HOST, RESIZER_KEY);
    	
        String fileSourceUrl = ORIGIN_HOST + "/" + resolvePathName(imageType, imageId) + imageId + "_ori.jpg";  
        
        ThumborUrlBuilder builder = flxMainThumbor.buildImage(fileSourceUrl);
        builder = builder.resize(width, height);

        String signedImageUrl = builder.toUrl(); 
        return signedImageUrl;
    }
    
    private String resolvePathName(String contentType, String contentId) {
        char separatorChar = '/';
        String contentKey = String.valueOf(contentId);
        int len = contentKey.length();
        len = len - 1;
        len = ((int) len / 2) * 2;
        char[] idChars = new char[len];
        contentKey.getChars(0, len, idChars, 0);
        
        //subtype prefix was a "bad call" motivated by skins
        StringBuffer dirName = new StringBuffer();
        
        dirName.append(contentType).append(separatorChar);        
        for (int i = 0; i < len; i += 2) {
            dirName.append(idChars[i]);
            if ((i + 1) < len) {
                dirName.append(idChars[i + 1]);
            }
            dirName.append(separatorChar);
        }
        
        return dirName.toString();
    }
	
}