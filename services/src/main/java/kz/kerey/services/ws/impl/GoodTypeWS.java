package kz.kerey.services.ws.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.jws.WebParam;
import javax.jws.WebService;

import kz.kerey.business.types.enums.GoodTypeProperty;
import kz.kerey.business.wrappers.GoodTypeWrapper;
import kz.kerey.constants.Constants;
import kz.kerey.exceptions.ServicesException;
import kz.kerey.exceptions.WebServiceException;
import kz.kerey.services.api.GoodTypeInterface;
import kz.kerey.validators.GoodTypeValidator;
import kz.kerey.validators.ValidatorException;

@WebService
public class GoodTypeWS implements GoodTypeInterface {

	@EJB
	GoodTypeInterface bean;
	
	GoodTypeValidator validator = GoodTypeValidator.getValidator();
	
	public void createGoodType(
			@WebParam(name="type")
			GoodTypeWrapper type) throws WebServiceException {
		try {
			validator.validate(type);
			bean.createGoodType(type);
		}
		catch (ValidatorException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
		catch (ServicesException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
	}

	public void deleteGoodType(
			@WebParam(name="id")
			Long id) throws WebServiceException {
		try {
			if (id==null || id==0)
				throw new WebServiceException(Constants.objectIsNull, "ID is null or empty");
			bean.deleteGoodType(id);
		}
		catch (ServicesException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
	}
	
	public List<GoodTypeWrapper> getGoodTypeList(
			@WebParam(name="paged")
			Boolean paged,
			@WebParam(name="pageNum")
			Integer pageNum, 
			@WebParam(name="perPage")
			Integer perPage) throws WebServiceException {
		if (paged == null
				|| (paged && (pageNum == null || pageNum == 0
						|| perPage == null || perPage == 0)))
			throw new WebServiceException(Constants.rangeIsIncorrect,
					"Request range is incorrect");
		try {
			return bean.getGoodTypeList(paged, pageNum, perPage);
		} catch (ServicesException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
	}

	public void changeGoodTypeProperty(
			@WebParam(name="id")
			Long id, 
			@WebParam(name="property")
			GoodTypeProperty property,
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
			bean.changeGoodTypeProperty(id, property, newValue);
		} catch (ServicesException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
	}

}
