package kz.kerey.business.wrappers

class GoodWrapper implements Serializable {

    Long id
	String name
	String primaryBarcode
	Boolean partialSelling
	Long countPerBox
	Long countSellable

	@Override
	String toString() {
		name + "(${primaryBarcode})"
	}
	
}
