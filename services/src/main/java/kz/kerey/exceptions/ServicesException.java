package kz.kerey.exceptions;

import kz.kerey.constants.Constants;

import javax.ejb.EJBException;

public class ServicesException extends EJBException {

	private static final long serialVersionUID = 5888590522434808725L;
	
	private Constants errorCode;
	private String comment;
	
	public ServicesException(Constants error, String comment) {
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