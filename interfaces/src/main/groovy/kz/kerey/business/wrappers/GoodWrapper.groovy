package kz.kerey.business.wrappers

import java.io.Serializable

import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
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
