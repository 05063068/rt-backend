package com.rottentomatoes.movieapi.utils;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.java.Log;

/** 
 * Provide a static property loader that can be used throughout the project (and not
 * just with spring beans ala @Value or @Autowire
 * 
 * @author Peter
 */
@Log
@Component
public class PropertyUtils {
	
	@Autowired 
	private Properties props;

	private static Properties staticProps;
	
	@PostConstruct
	private void init(){
		staticProps = props;
	}
		
	public static String getProperty(String key){
		if(staticProps != null){
			return staticProps.getProperty(key);
		}
		else {
			throw new IllegalStateException("PropertyUtils bean not yet initialized");
		}
	}
	
}
