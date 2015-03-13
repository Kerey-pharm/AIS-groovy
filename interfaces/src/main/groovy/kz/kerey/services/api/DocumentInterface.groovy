package kz.kerey.services.api

import kz.kerey.business.wrappers.DocumentWrapper

interface DocumentInterface {

	void saveDocument(DocumentWrapper doc)
	void deleteDocument(Long id)
	DocumentWrapper getDocument(Long id)
	
}
