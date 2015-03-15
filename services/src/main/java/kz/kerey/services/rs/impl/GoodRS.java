package kz.kerey.services.rs.impl;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import kz.kerey.business.types.PageParam;
import kz.kerey.business.types.enums.GoodProperty;
import kz.kerey.business.wrappers.GoodWrapper;
import kz.kerey.exceptions.ServicesException;
import kz.kerey.services.api.GoodInterface;
import kz.kerey.validators.GoodValidator;
import kz.kerey.validators.ValidatorException;

@Path("goods")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class GoodRS implements GoodInterface {

	public static Logger logger = Logger.getLogger(GoodRS.class.getName());
	
	@Context
	HttpServletRequest request;

	@Context
	HttpServletResponse response;
	
	@EJB
	private GoodInterface bean;
	
	GoodValidator validator = GoodValidator.getValidator();
	
	@POST
	public void createGood(GoodWrapper obj) {
		try {
			validator.validate(obj);
			bean.createGood(obj);
		} catch (ValidatorException ex) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST,
						ex.getComment());
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
		} catch (ServicesException ex) {
			try {
				response.sendError(HttpServletResponse.SC_CONFLICT,
						ex.getComment());
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
		}
	}

	@DELETE
	public void deleteGood(
			@QueryParam("id")
			Long id) {
		if (id == null || id == 0) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST,
						"ID is NULL or EMPTY");
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
			return;
		}
		try {
			bean.deleteGood(id);
		} catch (ServicesException ex) {
			try {
				response.sendError(HttpServletResponse.SC_CONFLICT,
						ex.getComment());
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
		}
	}

	@GET
	public List<GoodWrapper> getGoodList(PageParam params, 
			@QueryParam("nameFilter")
			String nameFilter,
			@QueryParam("barcode")
			String barcode) {
		if (params!=null)
		if (params.getPaged()==null || (params.getPaged() && (params.getPageNum()==null || params.getPageNum()==0 || params.getPerPage()==null || params.getPerPage()==0))) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Range is incorrect");
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
			return null;
		}
		try {
			return bean.getGoodList(params, nameFilter, barcode);
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

	@PUT
	public void changeGoodProperty(
			@QueryParam("id")
			Long id, 
			@QueryParam("property")
			GoodProperty property,
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
		if (property==null || newValue==null || newValue.trim().length()==0) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "PropertyName or newValue is NULL");
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
			return;
		}
		try {
			bean.changeGoodProperty(id, property, newValue);
		}
		catch (ServicesException ex) {
			try {
				response.sendError(HttpServletResponse.SC_CONFLICT, ex.getComment());
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
		}
	}


}