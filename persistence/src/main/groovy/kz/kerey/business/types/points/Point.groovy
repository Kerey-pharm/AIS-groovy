package kz.kerey.business.types.points

import javax.persistence.DiscriminatorColumn
import javax.persistence.DiscriminatorType
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.ManyToOne

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue("Point")
abstract class Point {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id
	
	String name
	String description

	@ManyToOne
	Location location

}