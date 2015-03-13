package kz.kerey.business.wrappers

import java.io.Serializable

import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
class LadderWrapper implements Serializable {

	Long id
	String name
	String comment
	Long executionTime

}
