package kz.kerey.services.ws.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.jws.WebParam;
import javax.jws.WebService;

import kz.kerey.business.types.enums.LocationProperty;
import kz.kerey.business.wrappers.LocationWrapper;
import kz.kerey.constants.Constants;
import kz.kerey.exceptions.ServicesException;
import kz.kerey.exceptions.WebServiceException;
import kz.kerey.services.api.LocationInterface;
import kz.kerey.validators.LocationValidator;
import kz.kerey.validators.ValidatorException;

@WebService
public class LocationWS implements LocationInterface {

	@EJB
	LocationInterface bean;
	
	LocationValidator validator = LocationValidator.getValidator();

	public void createLocation(
			@WebParam(name="location")
			LocationWrapper location) throws WebServiceException {
		try {
			validator.validate(location);
			bean.createLocation(location);
		} catch (ValidatorException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		} catch (ServicesException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
	}


	public void deleteLocation(
			@WebParam(name="id")
			Long id) throws WebServiceException {
		try {
			if (id == null || id == 0)
				throw new WebServiceException(Constants.objectIsNull,
						"ID is null or empty");
			bean.deleteLocation(id);
		} catch (ServicesException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
	}


	public void changeLocation(
			@WebParam(name="id")
			Long id, 
			@WebParam(name="property")
			LocationProperty property,
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
			bean.changeLocation(id, property, newValue);
		} catch (ServicesException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
	}


	public List<LocationWrapper> getLocationsList(
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
			return bean.getLocationsList(paged, pageNum, perPage);
		} catch (ServicesException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
	}

}
