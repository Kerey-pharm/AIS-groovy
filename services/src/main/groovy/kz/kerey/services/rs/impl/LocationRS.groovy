package kz.kerey.services.rs.impl

import kz.kerey.services.ejb.impl.LocationEJB

import javax.ejb.Local
import javax.ejb.Stateless
import java.util.logging.Logger

import javax.ejb.EJB
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.ws.rs.Consumes
import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType

import kz.kerey.business.types.enums.LocationProperty
import kz.kerey.business.wrappers.LocationWrapper
import kz.kerey.exceptions.ServicesException
import kz.kerey.validators.LocationValidator
import kz.kerey.validators.ValidatorException

@Stateless
@Local(LocationRS.class)
@Path("location")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class LocationRS {

	static def logger = Logger.getLogger(LocationRS.class.name)
	
	@Context
	HttpServletRequest request

	@Context
	HttpServletResponse response

    @EJB
    LocationEJB bean

	@POST
	void createLocation(LocationWrapper location) {
		try {
            LocationValidator.validator.validate(location)
			bean.createLocation(location)
		}
		catch (ValidatorException ex) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.comment)
			} catch (IOException e) {
				logger.severe(e.message)
			}
		}
		catch (ServicesException ex) {
			try {
				response.sendError(HttpServletResponse.SC_CONFLICT, ex.comment)
			} catch (IOException e) {
				logger.severe(e.message)
			}
		}
	}

	@DELETE
	void deleteLocation(
			@QueryParam("id")
			Long id) {
		if (id == null || id == 0) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST,
						"ID is NULL or EMPTY")
			} catch (IOException e) {
				logger.severe(e.message)
			}
			return
		}
		try {
			bean.deleteLocation(id)
		} catch (ServicesException ex) {
			try {
				response.sendError(HttpServletResponse.SC_CONFLICT,
						ex.comment)
			} catch (IOException e) {
				logger.severe(e.message)
			}
		}
	}

	@PUT
	void changeLocation(
			@QueryParam("id")
			Long id, 
			@QueryParam("property")
			LocationProperty property,
			@QueryParam("newValue")
			String newValue) {
		if (id==null || id==0) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID is NULL or EMPTY")
			} catch (IOException e) {
				logger.severe(e.message)
			}
			return
		}
		if (property==null || newValue==null || newValue.trim().length()==0) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "PropertyName or newValue is NULL")
			} catch (IOException e) {
				logger.severe(e.message)
			}
			return
		}
		try {
			bean.changeLocation(id, property, newValue)
		}
		catch (ServicesException ex) {
			try {
				response.sendError(HttpServletResponse.SC_CONFLICT, ex.comment)
			} catch (IOException e) {
				logger.severe(e.message)
			}
		}
	}

	@GET
	List<LocationWrapper> getLocationsList(
			@QueryParam("paged")
			Boolean paged,
			@QueryParam("pageNum")
			Integer pageNum, 
			@QueryParam("perPage")
			Integer perPage) {
		if (paged==null || (paged && (pageNum==null || pageNum==0 || perPage==null || perPage==0))) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Range is incorrect")
			} catch (IOException e) {
				logger.severe(e.message)
			}
			return null
		}
		try {
			return bean.getLocationsList(paged, pageNum, perPage)
		}
		catch (ServicesException ex) {
			try {
				response.sendError(HttpServletResponse.SC_CONFLICT, ex.comment)
			} catch (IOException e) {
				logger.severe(e.message)
			}
			return null
		}
	}

}
