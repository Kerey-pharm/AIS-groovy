package kz.kerey.business.wrappers

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class DistributorWrapper extends PointWrapper implements Serializable {

	String bin
	String bankAccount
	String bankAccountDescription

}