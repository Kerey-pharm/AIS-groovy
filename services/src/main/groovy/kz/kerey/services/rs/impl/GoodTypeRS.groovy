package kz.kerey.services.rs.impl

import kz.kerey.services.ejb.impl.GoodTypeEJB

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

import kz.kerey.validators.GoodTypeValidator
import kz.kerey.validators.ValidatorException
import kz.kerey.business.types.enums.GoodTypeProperty
import kz.kerey.business.wrappers.GoodTypeWrapper
import kz.kerey.exceptions.ServicesException

@Stateless
@Local(GoodTypeRS.class)
@Path("goodType")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class GoodTypeRS {

	def static logger = Logger.getLogger(DocumentRS.class.name)
	
	@Context
	HttpServletRequest request

	@Context
	HttpServletResponse response

    @EJB
    GoodTypeEJB bean
	
	@POST
	void createGoodType(GoodTypeWrapper goodType) {
        try {
            GoodTypeValidator.instance.validate(goodType)
			bean.createGoodType(goodType)
		} catch (ex) {
            if (ex instanceof RuntimeException) {
                response.sendError(HttpServletResponse.SC_CONFLICT, ex.getCause().comment)
            }
            else if (ex instanceof ValidatorException) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.comment)
            }
            else {
                response.sendError(HttpServletResponse.SC_CONFLICT, "Unknown Exception. Look logs")
            }
		}
	}
	
	@GET
	List<GoodTypeWrapper> getGoodTypeList(
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
			return bean.getGoodTypeList(paged, pageNum, perPage)
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
	
	@DELETE
	void deleteGoodType(
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
			bean.deleteGoodType(id)
		} catch (RuntimeException ex) {
			try {
				response.sendError(HttpServletResponse.SC_CONFLICT,
                        ex.getCause().comment)
			} catch (IOException e) {
				logger.severe(e.message)
			}
		}
	}

	@PUT
	void changeGoodTypeProperty(
			@QueryParam("id")
			Long id, 
			@QueryParam("property")
			GoodTypeProperty property, 
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
			bean.changeGoodTypeProperty(id, property, newValue)
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