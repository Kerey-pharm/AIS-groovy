package kz.kerey.validators

import kz.kerey.business.wrappers.TaskWrapper
import kz.kerey.constants.Constants

class TaskValidator extends Validator<TaskWrapper> {

	def static validator = new TaskValidator()
	private TaskValidator() {}
	
	@Override
	void validate(TaskWrapper t) throws ValidatorException {
		
		if (t==null)
			throw new ValidatorException(Constants.objectIsNull, "Task is NULL")
		if (t.getLadder()==null || t.getLadder()==0)
			throw new ValidatorException(Constants.fieldNotFilledProperly, "Task Ladder is NULL or EMPTY")
		
	}

}
