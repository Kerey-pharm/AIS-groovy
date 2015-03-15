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

import kz.kerey.business.types.enums.LadderProperty;
import kz.kerey.business.types.enums.StepProperty;
import kz.kerey.business.wrappers.LadderWrapper;
import kz.kerey.business.wrappers.StepWrapper;
import kz.kerey.exceptions.ServicesException;
import kz.kerey.services.api.FlowConfigurationInterface;
import kz.kerey.validators.LadderValidator;
import kz.kerey.validators.StepValidator;
import kz.kerey.validators.ValidatorException;

@Path("")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class FlowConfigurationRS implements FlowConfigurationInterface {

	public static Logger logger = Logger.getLogger(UserRS.class.getName());
	
	@Context
	HttpServletRequest request;

	@Context
	HttpServletResponse response;
	
	@EJB
	FlowConfigurationInterface bean;
	
	LadderValidator ladderValidator = LadderValidator.getValidator();
	
	StepValidator stepValidator = StepValidator.getValidator();

	@Path("ladders")
	@POST
	public void createLadder(LadderWrapper ladder) {
		try {
			ladderValidator.validate(ladder);
			bean.createLadder(ladder);
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

	@Path("ladders")
	@DELETE
	public void deleteLadder(
			@QueryParam("ladderId")
			Long ladderId) {
		if (ladderId==null || ladderId==0) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID is NULL or EMPTY");
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
			return;
		}
		try {
			bean.deleteLadder(ladderId);
		}
		catch (ServicesException ex) {
			try {
				response.sendError(HttpServletResponse.SC_CONFLICT, ex.getComment());
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
		}
	}

	@Path("ladders")
	@GET
	public List<LadderWrapper> getLadderList(
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
			return bean.getLadderList(paged, pageNum, perPage);
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

	@Path("ladders")
	@PUT
	public void changeLadderProperty(
			@QueryParam("ladderId")
			Long ladderId, 
			@QueryParam("property")
			LadderProperty property, 
			@QueryParam("newValue")
			String newValue) {
		if (ladderId==null || ladderId==0) {
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
			bean.changeLadderProperty(ladderId, property, newValue);
		}
		catch (ServicesException ex) {
			try {
				response.sendError(HttpServletResponse.SC_CONFLICT, ex.getComment());
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
		}
	}

	@Path("ladders/{ladderId}/steps")
	@POST
	public void createStep(StepWrapper step) {
		try {
			stepValidator.validate(step);
			bean.createStep(step);
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

	@Path("ladders/{ladderId}/steps")
	@DELETE
	public void deleteStep(
			@PathParam("ladderId")
			Long ladderId, 
			@QueryParam("stepId")
			Long stepId) {
		if (ladderId==null || ladderId==0) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ladder is NULL or EMPTY");
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
			return;
		}
		if (stepId==null || stepId==0) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID is NULL or EMPTY");
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
			return;
		}
		try {
			bean.deleteStep(ladderId, stepId);
		}
		catch (ServicesException ex) {
			try {
				response.sendError(HttpServletResponse.SC_CONFLICT, ex.getComment());
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
		}
	}

	@Path("ladders/{ladderId}/steps")
	@PUT
	public void changeStepProperty(
			@QueryParam("stepId")
			Long stepId, 
			@QueryParam("property")
			StepProperty property, 
			@QueryParam("newValue")
			String newValue) {
		if (stepId==null || stepId==0) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "stepID is NULL or EMPTY");
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
			bean.changeStepProperty(stepId, property, newValue);
		}
		catch (ServicesException ex) {
			try {
				response.sendError(HttpServletResponse.SC_CONFLICT, ex.getComment());
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
		}
	}

	@Path("ladders/{ladderId}/steps")
	@GET
	public List<StepWrapper> getLadderSteps(
			@PathParam("ladderId")
			Long ladderId) {
		if (ladderId==null || ladderId==0) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "LadderID is NULL or EMPTY");
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
			return null;
		}
		try {
			return bean.getLadderSteps(ladderId);
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

	@Path("ladders/{ladderId}/steps/{left}/{right}")
	@PUT
	public void swapLadderSteps(
			@PathParam("ladderId")
			Long ladder,
			@PathParam("left")
			Long left,
			@PathParam("right")
			Long right) {
		if (ladder==null || ladder==0) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "LadderID is NULL or EMPTY");
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
			return;
		}
		if (left==null || left==0) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "leftID is NULL or EMPTY");
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
			return;
		}
		if (right==null || right==0) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "rightID is NULL or EMPTY");
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
			return;
		}
		try {
			bean.swapLadderSteps(ladder, left, right);
		}
		catch (ServicesException ex) {
			try {
				response.sendError(HttpServletResponse.SC_CONFLICT, ex.getComment());
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
			return;
		}
	}
	
}