package dct.com.model;



public class UserResponse {
		 
	String sessionId;
	long serviceToken;
	UserDetailsModel dbDetail;
	
	public String getSessionId() {
		return sessionId;
	}
	public UserDetailsModel getDbDetail() {
		return dbDetail;
	}
	public void setDbDetail(UserDetailsModel dbDetail) {
		this.dbDetail = dbDetail;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public long getServiceToken() {
		return serviceToken;
	}
	public void setServiceToken(long serviceToken) {
		this.serviceToken = serviceToken;
	}
	
	

}
