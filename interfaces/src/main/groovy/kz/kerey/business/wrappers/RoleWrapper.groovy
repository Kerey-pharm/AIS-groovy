package kz.kerey.business.wrappers

import java.io.Serializable

import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
class RoleWrapper implements Serializable {

	Long id
	String name

	@Override
	String toString() {
		name
	}
	
}
