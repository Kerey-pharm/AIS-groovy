package kz.kerey.business.types

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class GoodItem {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id
	
	@ManyToOne
	GoodItem parent
	
	@ManyToOne
	Good good
	
	String barcode
	String serial
	Date initialDate
	Date expireDate
	Long initialBoxCount
	Long initialCount
	Long currentCount

}