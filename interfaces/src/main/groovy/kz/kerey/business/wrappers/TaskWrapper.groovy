package kz.kerey.business.wrappers

import kz.kerey.business.types.enums.TaskStatus

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
