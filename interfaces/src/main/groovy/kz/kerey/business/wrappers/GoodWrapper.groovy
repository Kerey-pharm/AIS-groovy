package kz.kerey.business.wrappers

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
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
