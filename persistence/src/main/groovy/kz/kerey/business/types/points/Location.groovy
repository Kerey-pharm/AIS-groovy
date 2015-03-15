package kz.kerey.business.types.points

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Location {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id
	
	String name

}