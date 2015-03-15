package kz.kerey.business.wrappers

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
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
