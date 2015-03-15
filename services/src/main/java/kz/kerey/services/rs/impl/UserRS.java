package kz.kerey.services.rs.impl;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import kz.kerey.business.types.enums.RoleProperty;
import kz.kerey.business.types.enums.UserProperty;
import kz.kerey.business.wrappers.RoleWrapper;
import kz.kerey.business.wrappers.UserWrapper;
import kz.kerey.exceptions.ServicesException;
import kz.kerey.exceptions.WebServiceException;
import kz.kerey.services.api.UserInterface;
import kz.kerey.validators.RoleValidator;
import kz.kerey.validators.UserValidator;
import kz.kerey.validators.ValidatorException;

@Path("")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class UserRS implements UserInterface {

	public static Logger logger = Logger.getLogger(UserRS.class.getName());
	
	@Context
	HttpServletRequest request;

	@Context
	HttpServletResponse response;
	
	@EJB
	UserInterface bean;
	
	UserValidator userValidator = UserValidator.getValidator();
	
	RoleValidator roleValidator = RoleValidator.getValidator();
	
	@Path("roles")
	@POST
	public void createRole(RoleWrapper obj) {
		try {
			roleValidator.validate(obj);
			bean.createRole(obj);
		}
		catch (ValidatorException ex) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getComment());
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
		}
		catch (ServicesException ex) {
			try {
				response.sendError(HttpServletResponse.SC_CONFLICT, ex.getComment());
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
		}
	}

	@Path("roles")
	@DELETE
	public void deleteRole(
			@QueryParam("id")
			Long id) {
		if (id==null || id==0) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID is NULL or EMPTY");
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
			return;
		}
		try {
			bean.deleteRole(id);
		}
		catch (ServicesException ex) {
			try {
				response.sendError(HttpServletResponse.SC_CONFLICT, ex.getComment());
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
		}
	}

	@Path("roles")
	@GET
	public List<RoleWrapper> getRoleList(
			@QueryParam("paged")
			Boolean paged, 
			@QueryParam("pageNum")
			Integer pageNum,
			@QueryParam("perPage")
			Integer perPage) {
		if (paged==null || (paged && (pageNum==null || pageNum==0 || perPage==null || perPage==0))) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Range is incorrect");
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
			return null;
		} 
		try {
			return bean.getRoleList(paged, pageNum, perPage);
		}
		catch (ServicesException ex) {
			try {
				response.sendError(HttpServletResponse.SC_CONFLICT, ex.getComment());
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
			return null;
		}
	}

	@Path("roles")
	@PUT
	public void changeRoleProperty(
			@QueryParam("id")
			Long id, 
			@QueryParam("propertyName")
			RoleProperty propertyName, 
			@QueryParam("newValue")
			String newValue) {
		if (id==null || id==0) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID is NULL or EMPTY");
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
			return;
		}
		if (propertyName==null || newValue==null || newValue.trim().length()==0) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "PropertyName or newValue is NULL");
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
			return;
		}
		try {
			bean.changeRoleProperty(id, propertyName, newValue);
		}
		catch (ServicesException ex) {
			try {
				response.sendError(HttpServletResponse.SC_CONFLICT, ex.getComment());
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
		}
	}

	@Path("users")
	@POST
	public void createUser(UserWrapper obj) {
		try {
			userValidator.validate(obj);
			bean.createUser(obj);
		}
		catch (ValidatorException ex) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getComment());
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
		}
		catch (ServicesException ex) {
			try {
				response.sendError(HttpServletResponse.SC_CONFLICT, ex.getComment());
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
		}
	}

	@Path("users")
	@DELETE
	public void deleteUser(
			@QueryParam("id")
			Long id) {
		if (id==null || id==0) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID is NULL or EMPTY");
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
			return;
		}
		try {
			bean.deleteUser(id);
		}
		catch (ServicesException ex) {
			try {
				response.sendError(HttpServletResponse.SC_CONFLICT, ex.getComment());
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
		}
	}

	@Path("users")
	@GET
	public List<UserWrapper> getUserList(
			@QueryParam("paged")
			Boolean paged,
			@QueryParam("pageNum")
			Integer pageNum,
			@QueryParam("perPage")
			Integer perPage) {
		if (paged==null || (paged && (pageNum==null || pageNum==0 || perPage==null || perPage==0))) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Range is incorrect");
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
			return null;
		} 
		try {
			return bean.getUserList(paged, pageNum, perPage);
		}
		catch (ServicesException ex) {
			try {
				response.sendError(HttpServletResponse.SC_CONFLICT, ex.getComment());
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
			return null;
		}
	}

	@Override
	public List<UserWrapper> getUserListFiltered(Boolean paged,
			Integer pageNum, Integer perPage, String filter) {
		return null;
	}

	@Path("users")
	@PUT
	public void changeUserProperty(
			@QueryParam("id")
			Long id,
			@QueryParam("propertyName")
			UserProperty propertyName,
			@QueryParam("newValue")
			String newValue) {
		if (id==null || id==0) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID is NULL or EMPTY");
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
			return;
		}
		if (propertyName==null || newValue==null || newValue.trim().length()==0) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "PropertyName or newValue is NULL");
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
			return;
		}
		try {
			bean.changeUserProperty(id, propertyName, newValue);
		}
		catch (ServicesException ex) {
			try {
				response.sendError(HttpServletResponse.SC_CONFLICT, ex.getComment());
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
		}
	}

	@Path("users/{userId}/{roleId}")
	@PUT
	public void addRoleToUser(
			@PathParam("userId")
			Long userId, 
			@PathParam("roleId")
			Long roleId) {
		if (userId==null || userId==0) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "UserID is NULL or EMPTY");
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
			return;
		}
		if (roleId==null || roleId==0) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "RoleID is NULL or EMPTY");
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
			return;
		}
		try {
			bean.addRoleToUser(userId, roleId);
		}
		catch (ServicesException ex) {
			try {
				response.sendError(HttpServletResponse.SC_CONFLICT, ex.getComment());
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
		}
	}

	@Path("users/{userId}/{roleId}")
	@DELETE
	public void removeRoleFromUser(
			@PathParam("userId")
			Long userId, 
			@PathParam("roleId")
			Long roleId) {
		if (userId==null || userId==0) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "UserID is NULL or EMPTY");
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
			return;
		}
		if (roleId==null || roleId==0) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "RoleID is NULL or EMPTY");
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
			return;
		}
		try {
			bean.removeRoleFromUser(userId, roleId);
		}
		catch (ServicesException ex) {
			try {
				response.sendError(HttpServletResponse.SC_CONFLICT, ex.getComment());
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
		}
	}

	@Path("users/{userId}/roles")
	@GET
	public List<RoleWrapper> getUserRolesList(
			@PathParam("userId")
			Long userId) {
		if (userId==null || userId==0) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "UserID is NULL or EMPTY");
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
			return null;
		}
		try {
			return bean.getUserRolesList(userId);
		}
		catch (ServicesException ex) {
			try {
				response.sendError(HttpServletResponse.SC_CONFLICT, ex.getComment());
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
			return null;
		}
	}

}
