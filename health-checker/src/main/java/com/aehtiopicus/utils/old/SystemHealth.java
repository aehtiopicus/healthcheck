package com.nuskin.ebiz.model;

import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Class for displaying health status of a particular system
 * <p/>
 * Created by Miguel Senosiain on 15/10/14.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class SystemHealth {
	
    private String systemName;
    
    private Boolean healthy;
    
    private String comment;
    
    private String systemCall;
    
    private Map<String,String>  detailedComments;
        

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public boolean isHealthy() {
        return healthy;
    }

    public void setHealthy(Boolean healthy) {
        this.healthy = healthy;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

	public String getSystemCall() {
		return systemCall;
	}

	public void setSystemCall(String systemCall) {
		this.systemCall = systemCall;
	}

	public Map<String, String> getDetailedComments() {
		return detailedComments;
	}

	public void setDetailedComments(Map<String, String> detailedComments) {
		this.detailedComments = detailedComments;
	}

    
    
}
