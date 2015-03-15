package kz.kerey.services.ejb.impl;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import kz.kerey.business.types.documents.Document;
import kz.kerey.business.wrappers.DocumentWrapper;
import kz.kerey.constants.Constants;
import kz.kerey.exceptions.ServicesException;
import kz.kerey.services.api.DocumentInterface;

@Stateless
@Default
@Remote(DocumentInterface.class)
public class DocumentEJB implements DocumentInterface {

	@Resource(mappedName = "java:jboss/drugstoreEntityManagerFactory")
	private EntityManagerFactory emf;
	
	@Override
	public void saveDocument(DocumentWrapper doc) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			Document obj = new Document();
			obj.setData(doc.getData());
			obj.setDocExtension(doc.getDocExtension());
			obj.setDocName(doc.getDocName());
			obj.setDocType(doc.getDocType());
			em.persist(obj);
		}
		finally {
			if (em!=null)
				if (em.isOpen())
					em.close();
		}
	}

	@Override
	public void deleteDocument(Long id) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			Document doc = em.find(Document.class, id);
			if (doc==null)
				throw new ServicesException(Constants.objectIsNull, "Document with id:" + id + " is NULL");
			em.remove(doc);
		}
		finally {
			if (em!=null)
				if (em.isOpen())
					em.close();
		}
	}

	@Override
	public DocumentWrapper getDocument(Long id) {
		EntityManager em = null;
		DocumentWrapper res = null;
		try {
			em = emf.createEntityManager();
			Document doc = em.find(Document.class, id);
			if (doc==null)
				throw new ServicesException(Constants.objectIsNull, "Document with id:" + id + " is NULL");
			res = new DocumentWrapper();
			res.setId(doc.getId());
			res.setData(doc.getData());
			res.setDocType(doc.getDocType());
			res.setDocExtension(doc.getDocExtension());
			res.setDocName(doc.getDocName());
		}
		finally {
			if (em!=null)
				if (em.isOpen())
					em.close();
		}
		return res;
	}

}
