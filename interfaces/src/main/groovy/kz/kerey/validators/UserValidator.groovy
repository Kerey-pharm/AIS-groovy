package kz.kerey.validators

import kz.kerey.business.wrappers.UserWrapper
import kz.kerey.constants.Constants

class UserValidator extends Validator<UserWrapper> {

	def static validator = new UserValidator()
	private UserValidator() {}
	
	@Override
	void validate(UserWrapper t) throws ValidatorException {
		if (t==null)
			throw new ValidatorException(Constants.objectIsNull, "User object is NULL")
		if (t.getLogin()==null || t.getLogin().trim().length()==0)
			throw new ValidatorException(Constants.fieldNotFilledProperly, "User login is NULL")
	}

}
