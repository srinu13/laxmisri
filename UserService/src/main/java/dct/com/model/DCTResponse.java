package dct.com.model;

public class DCTResponse {
	
	private String errMessage;
	private String result;
	private String role;
	private Object userList;
	private Object roleList;
	
	
	public Object getRoleList() {
		return roleList;
	}
	public void setRoleList(Object roleList) {
		this.roleList = roleList;
	}
	public Object getUserList() {
		return userList;
	}
	public void setUserList(Object userList) {
		this.userList = userList;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getErrMessage() {
		return errMessage;
	}
	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	

}
