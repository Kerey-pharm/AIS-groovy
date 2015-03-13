package kz.kerey.validators

import kz.kerey.business.wrappers.StepWrapper
import kz.kerey.constants.Constants

class StepValidator extends Validator<StepWrapper> {

	static StepValidator validator = new StepValidator()
	static StepValidator getValidator() {
		validator
	}
	
	@Override
	void validate(StepWrapper t) throws ValidatorException {
		if (t==null)
			throw new ValidatorException(Constants.objectIsNull, "Step is NULL")
		if (t.getName()==null || t.getName().trim().length()==0)
			throw new ValidatorException(Constants.fieldNotFilledProperly, "Step name is empty")
		if (t.getLadder()==null || t.getLadder()==0)
			throw new ValidatorException(Constants.fieldNotFilledProperly, "Step ladder is empty")
	}

}