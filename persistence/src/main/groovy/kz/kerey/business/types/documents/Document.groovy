package kz.kerey.business.types.documents

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Lob

@Entity
class Document {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id

	String docType
	String docExtension
	String docName

	@Lob
	byte[] data

}