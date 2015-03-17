package kz.kerey.validators

import kz.kerey.business.wrappers.TaskWrapper
import kz.kerey.constants.Constants

@Singleton
class TaskValidator implements Validator<TaskWrapper> {

	def validate(TaskWrapper t) throws ValidatorException {
		
		if (t==null)
			throw new ValidatorException(Constants.objectIsNull, "Task is NULL")
		if (t.getLadder()==null || t.getLadder()==0)
			throw new ValidatorException(Constants.fieldNotFilledProperly, "Task Ladder is NULL or EMPTY")
		
	}

}
