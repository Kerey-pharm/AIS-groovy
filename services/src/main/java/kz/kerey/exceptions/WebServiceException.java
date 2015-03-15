package kz.kerey.exceptions;

import kz.kerey.constants.Constants;

import javax.xml.ws.WebFault;

@WebFault
public class WebServiceException extends RuntimeException {

	private static final long serialVersionUID = -7776211548782675247L;
	
	private Constants errorCode;
	private String comment;
	
	public WebServiceException(Constants error, String comment) {
		this.errorCode = error;
		this.comment = comment;
	}
	
	public Constants getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(Constants errorCode) {
		this.errorCode = errorCode;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
