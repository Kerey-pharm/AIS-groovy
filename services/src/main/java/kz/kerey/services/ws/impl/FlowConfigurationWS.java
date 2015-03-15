package kz.kerey.services.ws.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.jws.WebParam;
import javax.jws.WebService;

import kz.kerey.business.types.enums.LadderProperty;
import kz.kerey.business.types.enums.StepProperty;
import kz.kerey.business.wrappers.LadderWrapper;
import kz.kerey.business.wrappers.StepWrapper;
import kz.kerey.constants.Constants;
import kz.kerey.exceptions.ServicesException;
import kz.kerey.exceptions.WebServiceException;
import kz.kerey.services.api.FlowConfigurationInterface;
import kz.kerey.validators.LadderValidator;
import kz.kerey.validators.StepValidator;
import kz.kerey.validators.ValidatorException;

@WebService
public class FlowConfigurationWS implements FlowConfigurationInterface {

	@EJB
	FlowConfigurationInterface bean;

	LadderValidator ladderValidator = LadderValidator.getValidator();

	StepValidator stepValidator = StepValidator.getValidator();

	public void createLadder(
			@WebParam(name="ladder")
			LadderWrapper ladder) throws WebServiceException {
		try {
			ladderValidator.validate(ladder);
			bean.createLadder(ladder);
		} catch (ValidatorException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		} catch (ServicesException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
	}

	public void deleteLadder(@WebParam(name = "id") Long id)
			throws WebServiceException {
		try {
			if (id == null || id == 0)
				throw new WebServiceException(Constants.objectIsNull,
						"ID is null or empty");
			bean.deleteLadder(id);
		} catch (ServicesException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
	}

	public List<LadderWrapper> getLadderList(
			@WebParam(name = "paged") Boolean paged,
			@WebParam(name = "pageNum") Integer pageNum,
			@WebParam(name = "perPage") Integer perPage)
			throws WebServiceException {
		if (paged == null
				|| (paged && (pageNum == null || pageNum == 0
						|| perPage == null || perPage == 0)))
			throw new WebServiceException(Constants.rangeIsIncorrect,
					"Request range is incorrect");
		try {
			return bean.getLadderList(paged, pageNum, perPage);
		} catch (ServicesException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
	}

	public void changeLadderProperty(@WebParam(name = "id") Long id,
			@WebParam(name = "property") LadderProperty property,
			@WebParam(name = "newValue") String newValue)
			throws WebServiceException {
		if (id == null || id == 0)
			throw new WebServiceException(Constants.objectIsNull,
					"ID is null or empty");
		if (property == null || newValue == null
				|| newValue.trim().length() == 0)
			throw new WebServiceException(Constants.fieldNotFilledProperly,
					"PropertyName or newValue is NULL");
		try {
			bean.changeLadderProperty(id, property, newValue);
		} catch (ServicesException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
	}

	public void createStep(
			@WebParam(name="step")
			StepWrapper step) throws WebServiceException {
		try {
			stepValidator.validate(step);
			bean.createStep(step);
		} catch (ValidatorException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		} catch (ServicesException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
	}

	public void deleteStep(@WebParam(name = "ladderId") Long ladderId,
			@WebParam(name = "id") Long id) throws WebServiceException {
		try {
			if (ladderId == null || ladderId == 0)
				throw new WebServiceException(Constants.objectIsNull,
						"ladderId is null or empty");
			if (id == null || id == 0)
				throw new WebServiceException(Constants.objectIsNull,
						"ID is null or empty");
			bean.deleteStep(ladderId, id);
		} catch (ServicesException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
	}

	public void changeStepProperty(@WebParam(name = "id") Long id,
			@WebParam(name = "property") StepProperty property,
			@WebParam(name = "newValue") String newValue)
			throws WebServiceException {
		if (id == null || id == 0)
			throw new WebServiceException(Constants.objectIsNull,
					"ID is null or empty");
		if (property == null || newValue == null
				|| newValue.trim().length() == 0)
			throw new WebServiceException(Constants.fieldNotFilledProperly,
					"PropertyName or newValue is NULL");
		try {
			bean.changeStepProperty(id, property, newValue);
		} catch (ServicesException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
	}

	public List<StepWrapper> getLadderSteps(@WebParam(name = "id") Long id)
			throws WebServiceException {
		if (id == null || id == 0)
			throw new WebServiceException(Constants.objectIsNull,
					"ID is null or empty");
		try {
			return bean.getLadderSteps(id);
		} catch (ServicesException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
	}

	public void swapLadderSteps(@WebParam(name = "ladderId") Long ladderId,
			@WebParam(name = "left") Long left,
			@WebParam(name = "right") Long right) throws WebServiceException {
		if (ladderId == null || ladderId == 0)
			throw new WebServiceException(Constants.objectIsNull,
					"ladderId ID is null or empty");
		if (left == null || left == 0)
			throw new WebServiceException(Constants.objectIsNull,
					"left ID is null or empty");
		if (right == null || right == 0)
			throw new WebServiceException(Constants.objectIsNull,
					"right ID is null or empty");
		try {
			bean.swapLadderSteps(ladderId, left, right);
		} catch (ServicesException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
	}

}
