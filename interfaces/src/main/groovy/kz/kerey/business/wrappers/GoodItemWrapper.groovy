package kz.kerey.business.wrappers

class GoodItemWrapper implements Serializable {

	Long id
	GoodItemWrapper parent
	GoodWrapper good
	String barcode
	String serial
	Date initialDate
	Date expireDate
	Long initialBoxCount
	Long initialCount
	Long currentCount

}
