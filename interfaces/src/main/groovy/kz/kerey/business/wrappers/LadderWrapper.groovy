package kz.kerey.business.wrappers

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class LadderWrapper implements Serializable {

	Long id
	String name
	String comment
	Long executionTime

}
