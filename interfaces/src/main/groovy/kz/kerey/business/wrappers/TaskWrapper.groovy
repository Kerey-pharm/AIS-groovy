package kz.kerey.business.wrappers

import kz.kerey.business.types.enums.TaskStatus

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType

import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class TaskWrapper implements Serializable {

	Long id
	Date initialDate
	Date finishDate
	Date deadlineDate
	TaskStatus status
	String barcode
	Long ladder
	Long step
	Long executor

}
