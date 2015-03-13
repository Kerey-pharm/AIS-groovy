package kz.kerey.business.wrappers

import java.io.Serializable

import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
class PointWrapper implements Serializable {

	Long id
	String name
	String description
	LocationWrapper location

}