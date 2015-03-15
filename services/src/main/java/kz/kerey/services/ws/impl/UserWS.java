package kz.kerey.services.ws.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.jws.WebParam;
import javax.jws.WebService;

import kz.kerey.business.types.enums.RoleProperty;
import kz.kerey.business.types.enums.UserProperty;
import kz.kerey.business.wrappers.RoleWrapper;
import kz.kerey.business.wrappers.UserWrapper;
import kz.kerey.constants.Constants;
import kz.kerey.exceptions.ServicesException;
import kz.kerey.exceptions.WebServiceException;
import kz.kerey.services.api.UserInterface;
import kz.kerey.validators.RoleValidator;
import kz.kerey.validators.UserValidator;
import kz.kerey.validators.ValidatorException;

@WebService
// @HandlerChain(file="/kz/kerey/services/ws/handlers/handlers.xml")
public class UserWS implements UserInterface {

	@EJB
	UserInterface bean;

	UserValidator userValidator = UserValidator.getValidator();

	RoleValidator roleValidator = RoleValidator.getValidator();

	public void createRole(
			@WebParam(name="role")
			RoleWrapper role) throws WebServiceException {
		try {
			roleValidator.validate(role);
			bean.createRole(role);
		} catch (ValidatorException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		} catch (ServicesException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
	}

	public void deleteRole(@WebParam(name = "id") Long id)
			throws WebServiceException {
		try {
			if (id == null || id == 0)
				throw new WebServiceException(Constants.objectIsNull,
						"ID is null or empty");
			bean.deleteRole(id);
		} catch (ServicesException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
	}

	public List<RoleWrapper> getRoleList(
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
			return bean.getRoleList(paged, pageNum, perPage);
		} catch (ServicesException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
	}

	public void changeRoleProperty(@WebParam(name = "id") Long id,
			@WebParam(name = "propertyName") RoleProperty propertyName,
			@WebParam(name = "newValue") String newValue)
			throws WebServiceException {
		if (id == null || id == 0)
			throw new WebServiceException(Constants.objectIsNull,
					"ID is null or empty");
		if (propertyName == null || newValue == null
				|| newValue.trim().length() == 0)
			throw new WebServiceException(Constants.fieldNotFilledProperly,
					"PropertyName or newValue is NULL");
		try {
			bean.changeRoleProperty(id, propertyName, newValue);
		} catch (ServicesException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
	}

	public void createUser(
			@WebParam(name="user")
			UserWrapper user) throws WebServiceException {
		try {
			userValidator.validate(user);
			bean.createUser(user);
		} catch (ValidatorException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		} catch (ServicesException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
	}

	public void deleteUser(@WebParam(name = "id") Long id)
			throws WebServiceException {
		try {
			if (id == null || id == 0)
				throw new WebServiceException(Constants.objectIsNull,
						"ID is null or empty");
			bean.deleteUser(id);
		} catch (ServicesException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
	}

	public List<UserWrapper> getUserList(
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
			return bean.getUserList(paged, pageNum, perPage);
		} catch (ServicesException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
	}

	public List<UserWrapper> getUserListFiltered(
			@WebParam(name = "paged") Boolean paged,
			@WebParam(name = "pageNum") Integer pageNum,
			@WebParam(name = "perPage") Integer perPage,
			@WebParam(name = "filter") String filter)
			throws WebServiceException {
		return this.getUserList(paged, pageNum, perPage);
	}

	public void changeUserProperty(@WebParam(name = "id") Long id,
			@WebParam(name = "propertyName") UserProperty propertyName,
			@WebParam(name = "newValue") String newValue)
			throws WebServiceException {
		if (id == null || id == 0)
			throw new WebServiceException(Constants.objectIsNull,
					"ID is null or empty");
		if (propertyName == null || newValue == null
				|| newValue.trim().length() == 0)
			throw new WebServiceException(Constants.fieldNotFilledProperly,
					"PropertyName or newValue is NULL");
		try {
			bean.changeUserProperty(id, propertyName, newValue);
		} catch (ServicesException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
	}

	public void addRoleToUser(@WebParam(name = "userId") Long userId,
			@WebParam(name = "roleId") Long roleId) throws WebServiceException {
		if (userId == null || userId == 0)
			throw new WebServiceException(Constants.objectIsNull,
					"UserID is null or empty");
		if (roleId == null || roleId == 0)
			throw new WebServiceException(Constants.objectIsNull,
					"RoleID is null or empty");
		try {
			bean.addRoleToUser(userId, roleId);
		} catch (ServicesException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
	}

	public void removeRoleFromUser(@WebParam(name = "userId") Long userId,
			@WebParam(name = "roleId") Long roleId) throws WebServiceException {
		if (userId == null || userId == 0)
			throw new WebServiceException(Constants.objectIsNull,
					"UserID is null or empty");
		if (roleId == null || roleId == 0)
			throw new WebServiceException(Constants.objectIsNull,
					"RoleID is null or empty");
		try {
			bean.removeRoleFromUser(userId, roleId);
		} catch (ServicesException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
	}

	public List<RoleWrapper> getUserRolesList(@WebParam(name = "id") Long id)
			throws WebServiceException {
		if (id == null || id == 0)
			throw new WebServiceException(Constants.objectIsNull,
					"ID is null or empty");
		try {
			return bean.getUserRolesList(id);
		} catch (ServicesException ex) {
			throw new WebServiceException(ex.getErrorCode(), ex.getComment());
		}
	}

}
