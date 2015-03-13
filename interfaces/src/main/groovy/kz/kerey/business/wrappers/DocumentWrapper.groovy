package kz.kerey.business.wrappers

import java.io.Serializable

import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
class DocumentWrapper implements Serializable {

	Long id
	String docType
	String docExtension
	String docName
	byte[] data

}
