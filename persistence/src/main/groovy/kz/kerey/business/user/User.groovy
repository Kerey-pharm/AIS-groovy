package kz.kerey.business.user

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name="K_USER")
class User {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	Long id

	@OneToMany
	Set<Role> roles

	String name
	String lastName
	String email
	String cellPhone
	Date availableFromDate
	Boolean active
    String login
    String passwordHash

}