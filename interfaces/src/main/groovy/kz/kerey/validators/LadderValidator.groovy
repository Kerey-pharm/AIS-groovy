package kz.kerey.validators

import kz.kerey.business.wrappers.LadderWrapper
import kz.kerey.constants.Constants

class LadderValidator extends Validator<LadderWrapper> {

	def static validator = new LadderValidator();
	private LadderValidator() {}
	
	@Override
	void validate(LadderWrapper t) throws ValidatorException {
		if (t==null)
			throw new ValidatorException(Constants.objectIsNull, "Ladder is NULL")
		if (t.getName()==null || t.getName().trim().length()==0)
			throw new ValidatorException(Constants.fieldNotFilledProperly, "Ladder name is empty")
	}

}