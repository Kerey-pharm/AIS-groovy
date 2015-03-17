package kz.kerey.services.rs.impl

import kz.kerey.services.ejb.impl.FlowConfigurationEJB

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
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType

import kz.kerey.business.types.enums.LadderProperty
import kz.kerey.business.types.enums.StepProperty
import kz.kerey.business.wrappers.LadderWrapper
import kz.kerey.business.wrappers.StepWrapper
import kz.kerey.exceptions.ServicesException
import kz.kerey.validators.LadderValidator
import kz.kerey.validators.StepValidator
import kz.kerey.validators.ValidatorException

@Stateless
@Local(FlowConfigurationRS.class)
@Path("")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class FlowConfigurationRS {

	static def logger = Logger.getLogger(UserRS.class.name)
	
	@Context
	HttpServletRequest request

	@Context
	HttpServletResponse response

    @EJB
    FlowConfigurationEJB bean
	
	@Path("ladders")
	@POST
	void createLadder(LadderWrapper ladder) {
		try {
            LadderValidator.instance.validate(ladder)
			bean.createLadder(ladder)
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

	@Path("ladders")
	@DELETE
	void deleteLadder(
			@QueryParam("ladderId")
			Long ladderId) {
		if (ladderId==null || ladderId==0) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID is NULL or EMPTY")
			} catch (IOException e) {
				logger.severe(e.message)
			}
			return
		}
		try {
			bean.deleteLadder(ladderId)
		}
		catch (ServicesException ex) {
			try {
				response.sendError(HttpServletResponse.SC_CONFLICT, ex.comment)
			} catch (IOException e) {
				logger.severe(e.message)
			}
		}
	}

	@Path("ladders")
	@GET
	List<LadderWrapper> getLadderList(
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
			return bean.getLadderList(paged, pageNum, perPage)
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

	@Path("ladders")
	@PUT
	void changeLadderProperty(
			@QueryParam("ladderId")
			Long ladderId, 
			@QueryParam("property")
			LadderProperty property, 
			@QueryParam("newValue")
			String newValue) {
		if (ladderId==null || ladderId==0) {
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
			bean.changeLadderProperty(ladderId, property, newValue)
		}
		catch (ServicesException ex) {
			try {
				response.sendError(HttpServletResponse.SC_CONFLICT, ex.comment)
			} catch (IOException e) {
				logger.severe(e.message)
			}
		}
	}

	@Path("ladders/{ladderId}/steps")
	@POST
	void createStep(StepWrapper step) {
		try {
            StepValidator.instance.validate(step)
			bean.createStep(step)
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

	@Path("ladders/{ladderId}/steps")
	@DELETE
	void deleteStep(
			@PathParam("ladderId")
			Long ladderId, 
			@QueryParam("stepId")
			Long stepId) {
		if (ladderId==null || ladderId==0) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ladder is NULL or EMPTY")
			} catch (IOException e) {
				logger.severe(e.message)
			}
			return
		}
		if (stepId==null || stepId==0) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID is NULL or EMPTY")
			} catch (IOException e) {
				logger.severe(e.message)
			}
			return
		}
		try {
			bean.deleteStep(ladderId, stepId)
		}
		catch (ServicesException ex) {
			try {
				response.sendError(HttpServletResponse.SC_CONFLICT, ex.comment)
			} catch (IOException e) {
				logger.severe(e.message)
			}
		}
	}

	@Path("ladders/{ladderId}/steps")
	@PUT
	void changeStepProperty(
			@QueryParam("stepId")
			Long stepId, 
			@QueryParam("property")
			StepProperty property, 
			@QueryParam("newValue")
			String newValue) {
		if (stepId==null || stepId==0) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "stepID is NULL or EMPTY")
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
			bean.changeStepProperty(stepId, property, newValue)
		}
		catch (ServicesException ex) {
			try {
				response.sendError(HttpServletResponse.SC_CONFLICT, ex.comment)
			} catch (IOException e) {
				logger.severe(e.message)
			}
		}
	}

	@Path("ladders/{ladderId}/steps")
	@GET
	List<StepWrapper> getLadderSteps(
			@PathParam("ladderId")
			Long ladderId) {
		if (ladderId==null || ladderId==0) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "LadderID is NULL or EMPTY")
			} catch (IOException e) {
				logger.severe(e.message)
			}
			return null
		}
		try {
			return bean.getLadderSteps(ladderId)
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

	@Path("ladders/{ladderId}/steps/{left}/{right}")
	@PUT
	void swapLadderSteps(
			@PathParam("ladderId")
			Long ladder,
			@PathParam("left")
			Long left,
			@PathParam("right")
			Long right) {
		if (ladder==null || ladder==0) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "LadderID is NULL or EMPTY")
			} catch (IOException e) {
				logger.severe(e.message)
			}
			return
		}
		if (left==null || left==0) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "leftID is NULL or EMPTY")
			} catch (IOException e) {
				logger.severe(e.message)
			}
			return
		}
		if (right==null || right==0) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "rightID is NULL or EMPTY")
			} catch (IOException e) {
				logger.severe(e.message)
			}
			return
		}
		try {
			bean.swapLadderSteps(ladder, left, right)
		}
		catch (ServicesException ex) {
			try {
				response.sendError(HttpServletResponse.SC_CONFLICT, ex.comment)
			} catch (IOException e) {
				logger.severe(e.message)
			}
			return
		}
	}
	
}