package kz.kerey.business.wrappers

import java.io.Serializable

import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
class GoodTypeWrapper implements Serializable {

	Long id
	String name

	@Override
	String toString() {
		(id!=null && id!=0) ? name : "ПУСТО"
	}
	
}
