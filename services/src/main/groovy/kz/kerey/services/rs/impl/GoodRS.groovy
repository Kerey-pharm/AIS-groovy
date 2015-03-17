package kz.kerey.services.rs.impl

import kz.kerey.services.ejb.impl.GoodEJB

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

import kz.kerey.business.types.PageParam
import kz.kerey.business.types.enums.GoodProperty
import kz.kerey.business.wrappers.GoodWrapper
import kz.kerey.exceptions.ServicesException
import kz.kerey.validators.GoodValidator
import kz.kerey.validators.ValidatorException

@Stateless
@Local(GoodRS.class)
@Path("goods")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class GoodRS {

	static def logger = Logger.getLogger(GoodRS.class.name)
	
	@Context
	HttpServletRequest request

	@Context
	HttpServletResponse response

    @EJB
    GoodEJB bean

	@POST
	void createGood(GoodWrapper obj) {
		try {
            GoodValidator.instance.validate(obj)
			bean.createGood(obj)
		} catch (ValidatorException ex) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST,
						ex.comment)
			} catch (IOException e) {
				logger.severe(e.message)
			}
		} catch (ServicesException ex) {
			try {
				response.sendError(HttpServletResponse.SC_CONFLICT,
						ex.comment)
			} catch (IOException e) {
				logger.severe(e.message)
			}
		}
	}

	@DELETE
	void deleteGood(
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
			bean.deleteGood(id)
		} catch (ServicesException ex) {
			try {
				response.sendError(HttpServletResponse.SC_CONFLICT,
						ex.comment)
			} catch (IOException e) {
				logger.severe(e.message)
			}
		}
	}

	@GET
	List<GoodWrapper> getGoodList(PageParam params, 
			@QueryParam("nameFilter")
			String nameFilter,
			@QueryParam("barcode")
			String barcode) {
		if (params!=null)
		if (params.paged==null || (params.paged && (params.pageNum==null || params.pageNum==0 || params.perPage==null || params.perPage==0))) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Range is incorrect")
			} catch (IOException e) {
				logger.severe(e.message)
			}
			return null
		}
		try {
			return bean.getGoodList(params, nameFilter, barcode)
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

	@PUT
	void changeGoodProperty(
			@QueryParam("id")
			Long id, 
			@QueryParam("property")
			GoodProperty property,
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
			bean.changeGoodProperty(id, property, newValue)
		}
		catch (ServicesException ex) {
			try {
				response.sendError(HttpServletResponse.SC_CONFLICT, ex.comment)
			} catch (IOException e) {
				logger.severe(e.message)
			}
		}
	}


}