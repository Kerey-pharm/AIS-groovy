package kz.kerey.business.types.points

import javax.persistence.Column
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("Distributor")
class Distributor extends Point {

	String bin
	String bankAccount
	String bankAccountDescription

}