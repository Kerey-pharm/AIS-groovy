package kz.kerey.business.types

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany

@Entity
class Good {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id

    @ManyToMany(fetch=FetchType.LAZY, targetEntity=GoodType.class)
    List<GoodType> types

	String name
	String primaryBarcode
	Boolean partialSelling
	Long countPerBox
	Long countSellable

}