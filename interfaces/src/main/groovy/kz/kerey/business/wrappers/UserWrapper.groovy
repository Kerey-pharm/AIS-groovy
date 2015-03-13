package kz.kerey.business.wrappers

import java.io.Serializable
import java.util.Date
import java.util.Set

import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
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
