package kz.kerey.services.ws.impl;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import kz.kerey.business.wrappers.DocumentWrapper;
import kz.kerey.constants.Constants;
import kz.kerey.exceptions.ServicesException;
import kz.kerey.exceptions.WebServiceException;
import kz.kerey.services.api.DocumentInterface;
import kz.kerey.validators.DocumentValidator;
import kz.kerey.validators.ValidatorException;

@WebService
public class DocumentWS implements DocumentInterface {

	@EJB
	DocumentInterface bean;
	
	DocumentValidator validator = DocumentValidator.getValidator();
	
	@WebMethod
	public void saveDocument(
			@WebParam(name="document")
			DocumentWrapper document) throws WebServiceException {
		try {
			validator.validate(document);
			bean.saveDocument(document);
		}
		catch (ValidatorException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
		catch (ServicesException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
	}

	@WebMethod
	public void deleteDocument(
			@WebParam(name="id")
			Long id) throws WebServiceException {
		try {
			if (id==null || id==0)
				throw new WebServiceException(Constants.objectIsNull, "ID is null or empty");
			bean.deleteDocument(id);
		}
		catch (ServicesException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
	}

	@WebMethod
	public DocumentWrapper getDocument(
			@WebParam(name="id")
			Long id) throws WebServiceException {
		try {
			if (id==null || id==0)
				throw new WebServiceException(Constants.objectIsNull, "ID is null or empty");
			return bean.getDocument(id);
		}
		catch (ServicesException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
	}

}