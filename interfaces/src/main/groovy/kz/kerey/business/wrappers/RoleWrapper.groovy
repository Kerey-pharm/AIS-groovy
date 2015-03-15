package kz.kerey.business.wrappers

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class RoleWrapper implements Serializable {

	Long id
	String name

	@Override
	String toString() {
		name
	}
	
}
