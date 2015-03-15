package kz.kerey.services.ws.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.jws.WebParam;
import javax.jws.WebService;

import kz.kerey.business.types.PageParam;
import kz.kerey.business.types.enums.GoodProperty;
import kz.kerey.business.wrappers.GoodWrapper;
import kz.kerey.constants.Constants;
import kz.kerey.exceptions.ServicesException;
import kz.kerey.exceptions.WebServiceException;
import kz.kerey.services.api.GoodInterface;
import kz.kerey.validators.GoodValidator;
import kz.kerey.validators.ValidatorException;

@WebService
public class GoodWS implements GoodInterface {

	@EJB
	private GoodInterface bean;
	
	GoodValidator validator = GoodValidator.getValidator();
	
	public void createGood(
			@WebParam(name="good")
			GoodWrapper good) throws WebServiceException {
		try {
			validator.validate(good);
			bean.createGood(good);
		}
		catch (ValidatorException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
		catch (ServicesException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
	}

	public void deleteGood(
			@WebParam(name="id")
			Long id) throws WebServiceException {
		try {
			if (id==null || id==0)
				throw new WebServiceException(Constants.objectIsNull, "ID is null or empty");
			bean.deleteGood(id);
		}
		catch (ServicesException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
	}

	public List<GoodWrapper> getGoodList(
			@WebParam(name="params")
			PageParam params, 
			@WebParam(name="nameFilter")
			String nameFilter,
			@WebParam(name="barcode")
			String barcode) throws WebServiceException {
		if (params!=null)
		if (params.getPaged() == null
				|| (params.getPaged() && (params.getPageNum() == null || params.getPageNum() == 0
						|| params.getPerPage() == null || params.getPerPage() == 0)))
			throw new WebServiceException(Constants.rangeIsIncorrect,
					"Request range is incorrect");
		try {
			return bean.getGoodList(params, nameFilter, barcode);
		} catch (ServicesException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
	}

	public void changeGoodProperty(
			@WebParam(name="id")
			Long id, 
			@WebParam(name="property")
			GoodProperty property,
			@WebParam(name="newValue")
			String newValue) throws WebServiceException {
		if (id == null || id == 0)
			throw new WebServiceException(Constants.objectIsNull,
					"ID is null or empty");
		if (property == null || newValue == null
				|| newValue.trim().length() == 0)
			throw new WebServiceException(Constants.fieldNotFilledProperly,
					"PropertyName or newValue is NULL");
		try {
			bean.changeGoodProperty(id, property, newValue);
		} catch (ServicesException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
	}

}
