package kz.kerey.business.wrappers

import java.io.Serializable

import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
class StepWrapper implements Serializable {

	Long id
	String name
	String comment
	Long ladder

}
