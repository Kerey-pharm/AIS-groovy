package kz.kerey.business.user

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name="K_ROLE")
class Role {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	Long id
	String name

}