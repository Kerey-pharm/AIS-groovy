package kz.kerey.business.wrappers

import java.io.Serializable

import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
class DistributorWrapper extends PointWrapper implements Serializable {

	String bin
	String bankAccount
	String bankAccountDescription

}