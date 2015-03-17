package kz.kerey.business.wrappers

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
