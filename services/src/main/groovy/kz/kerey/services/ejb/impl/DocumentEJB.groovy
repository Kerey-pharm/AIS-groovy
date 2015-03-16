package kz.kerey.services.ejb.impl

import javax.annotation.Resource
import javax.ejb.Local

import kz.kerey.business.types.documents.Document
import kz.kerey.business.wrappers.DocumentWrapper
import kz.kerey.constants.Constants
import kz.kerey.exceptions.ServicesException

import javax.ejb.Stateless

@Stateless
@Local(DocumentEJB.class)
class DocumentEJB {

	@Resource(mappedName = "java:jboss/drugstoreEntityManagerFactory")
    def emf

	void saveDocument(DocumentWrapper doc) {
		def em
		try {
			em = emf.createEntityManager()
			def obj = new Document(
                    data: doc.data,
                    docExtension: doc.docExtension,
                    docName: doc.docName,
                    docType: doc.docType)
			em.persist(obj);
		}
		finally {
            if (em!=null && em.isOpen())
                em.close()
		}
	}

	void deleteDocument(Long id) {
		def em = null
		try {
			em = emf.createEntityManager()
			def doc = em.find(Document.class, id)
			if (doc==null)
				throw new ServicesException(Constants.objectIsNull, "Document with id:${id} is NULL")
			em.remove(doc)
		}
		finally {
			if (em!=null && em.isOpen())
                em.close()
		}
	}

	DocumentWrapper getDocument(Long id) {
		def em
		def res
		try {
			em = emf.createEntityManager()
			def doc = em.find(Document.class, id)
			if (doc==null)
				throw new ServicesException(Constants.objectIsNull, "Document with id:${id} is NULL")
			res = new DocumentWrapper(
                    id: doc.id,
                    data: doc.data,
                    docType: doc.docType,
                    docExtension: doc.docExtension,
                    docName: doc.docName)
		}
		finally {
            if (em!=null && em.isOpen())
                em.close()
		}
		return res;
	}

}