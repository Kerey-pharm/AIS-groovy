package kz.kerey.services.rs.impl

import kz.kerey.services.ejb.impl.DocumentEJB

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
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType

import kz.kerey.business.wrappers.DocumentWrapper
import kz.kerey.exceptions.ServicesException
import kz.kerey.validators.DocumentValidator
import kz.kerey.validators.ValidatorException

@Stateless
@Local(DocumentRS.class)
@Path("documents")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class DocumentRS {

	static def logger = Logger.getLogger(DocumentRS.class.getName())

	@Context
	HttpServletRequest request

	@Context
	HttpServletResponse response

    @EJB
    DocumentEJB bean

	@POST
	void saveDocument(DocumentWrapper doc) {
		try {
            DocumentValidator.instance.validate(doc)
			bean.saveDocument(doc)
		} catch (ValidatorException ex) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.comment)
			} catch (IOException e) {
				logger.severe(e.message)
			}
		} catch (ServicesException ex) {
			try {
				response.sendError(HttpServletResponse.SC_CONFLICT, ex.comment)
			} catch (IOException e) {
				logger.severe(e.message)
			}
		}
	}

	@DELETE
	void deleteDocument(@QueryParam("docId") Long docId) {
		if (docId == null || docId == 0) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID is NULL or EMPTY")
			} catch (IOException e) {
				logger.severe(e.message)
			}
			return
		}
		try {
			bean.deleteDocument(docId)
		} catch (ServicesException ex) {
			try {
				response.sendError(HttpServletResponse.SC_CONFLICT, ex.comment)
			} catch (IOException e) {
				logger.severe(e.message)
			}
		}
	}

	@GET
	DocumentWrapper getDocument(@QueryParam("docId") Long docId) {
		if (docId == null || docId == 0) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID is NULL or EMPTY")
			} catch (IOException e) {
				logger.severe(e.message)
			}
			return null
		}
		try {
			return bean.getDocument(docId)
		} catch (ServicesException ex) {
			try {
				response.sendError(HttpServletResponse.SC_CONFLICT, ex.comment)
			} catch (IOException e) {
				logger.severe(e.message)
			}
			return null
		}
	}

}