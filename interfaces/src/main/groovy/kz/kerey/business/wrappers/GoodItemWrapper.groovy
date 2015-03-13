package kz.kerey.business.wrappers

import java.io.Serializable
import java.util.Date

import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
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
