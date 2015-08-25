package com.nuskin.ebiz.utils.error.handling;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.util.CollectionUtils;

public class ServerResponseUtils {

	
	public static ResponseErrorMessage convertToNormalizedErrorMessage(Integer statusCode, String errorMessage){
		
		ResponseErrorMessage jem = new ResponseErrorMessage();
		jem.setStatus(statusCode);
		
		String newErrorMessage = tryToConvertToJson(errorMessage);

		jem.setError(newErrorMessage != null ? newErrorMessage : errorMessage); 		
		return jem;

	}
	
	public static <T> ResponseErrorMessage convertToNormalizedErrorMessage(Integer statusCode, T errorMessage){
		
		ResponseErrorMessage jem = new ResponseErrorMessage();
		jem.setStatus(statusCode);		
//		jem.setErrors(errorMessage); 
		getAllData(errorMessage);
		return jem;

	}
	
	private static <T>ResponseErrorInnerMessage getAllData(T errorMessage,ResponseErrorInnerMessage errorData){
		
		ResponseErrorInnerMessage reim = null;
		if(errorMessage != null){
			reim = new ResponseErrorInnerMessage();
			for(Field f : errorMessage.getClass().getDeclaredFields()){				
				try{
					f.setAccessible(true);
					if(Date.class.isAssignableFrom(f.getType()) || String.class.isAssignableFrom(f.getType()) || Number.class.isAssignableFrom(f.getType()) || Boolean.class.isAssignableFrom(f.getType())){
						
						reim.setError(f.get(errorMessage).toString());						
						errorData.add(reim);
					}else{
						
					}
					
					
					
					
					if(f.getAnnotation(XmlElement.class) != null){
						if(Collection.class.isAssignableFrom(f.getType())){
							if(!CollectionUtils.isEmpty((Collection<?>)f.get(errorMessage))){
								for(Object o :(Collection<?>)f.get(errorMessage)){
									if(Collection.class.isAssignableFrom(f.getType())){
										for(Field f2 : o.getClass().getDeclaredFields()){
											f2.setAccessible(true);
											
										}
									}
								}
							}
						}
					}
					if(Collection.class.isAssignableFrom(f.getType())){
						
					}else{
						f.getClass().getCanonicalName();
					}
				}catch(Exception e){
					
				}
			}
		}
		return reim;
	}
	
	public static void main(String args[]){
		Integer t  = new Integer(2);
		ServerResponseUtils.convertToNormalizedErrorMessage(1, t);
	}
	
	
	private static String tryToConvertToJson(String errorMessage){
		Object jsonObject = null;
		 try {
			 jsonObject = new JSONObject(errorMessage);
		    } catch (JSONException ex) {
		        try {
		        	jsonObject =   new JSONArray(errorMessage);
		        } catch (JSONException ex1) {		            
		        }
		    }
		
		 return jsonObject != null ? jsonObject.toString() : null;
	}
	
}
