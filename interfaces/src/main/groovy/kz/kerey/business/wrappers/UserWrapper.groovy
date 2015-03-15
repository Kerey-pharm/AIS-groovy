package kz.kerey.business.wrappers

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType

import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class UserWrapper implements Serializable {

	Long id
	String login
	String passwordHash
	Set<RoleWrapper> roles
	String name
	String lastName
	String email
	String cellPhone
	Date availableFromDate
	Boolean active

}
