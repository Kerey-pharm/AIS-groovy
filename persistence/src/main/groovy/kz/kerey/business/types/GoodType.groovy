package kz.kerey.business.types

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class GoodType {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id
	String name

}