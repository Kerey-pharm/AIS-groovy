package kz.kerey.services.rs.impl;

import java.io.IOException;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import kz.kerey.business.wrappers.DocumentWrapper;
import kz.kerey.exceptions.ServicesException;
import kz.kerey.services.api.DocumentInterface;
import kz.kerey.validators.DocumentValidator;
import kz.kerey.validators.ValidatorException;

@Path("documents")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class DocumentRS implements DocumentInterface {

	public static Logger logger = Logger.getLogger(DocumentRS.class.getName());

	@Context
	HttpServletRequest request;

	@Context
	HttpServletResponse response;

	@EJB
	DocumentInterface bean;

	DocumentValidator validator = DocumentValidator.getValidator();

	@POST
	public void saveDocument(DocumentWrapper doc) {
		try {
			validator.validate(doc);
			bean.saveDocument(doc);
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
	public void deleteDocument(@QueryParam("docId") Long docId) {
		if (docId == null || docId == 0) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST,
						"ID is NULL or EMPTY");
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
			return;
		}
		try {
			bean.deleteDocument(docId);
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
	public DocumentWrapper getDocument(@QueryParam("docId") Long docId) {
		if (docId == null || docId == 0) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST,
						"ID is NULL or EMPTY");
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
			return null;
		}
		try {
			return bean.getDocument(docId);
		} catch (ServicesException ex) {
			try {
				response.sendError(HttpServletResponse.SC_CONFLICT,
						ex.getComment());
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
			return null;
		}
	}

}
