package kz.kerey.validators

import kz.kerey.business.wrappers.UserWrapper
import kz.kerey.constants.Constants

class UserValidator extends Validator<UserWrapper> {

	static UserValidator validator = new UserValidator()
	static UserValidator getValidator() {
		validator
	}
	
	@Override
	void validate(UserWrapper t) throws ValidatorException {
		if (t==null)
			throw new ValidatorException(Constants.objectIsNull, "User object is NULL")
		if (t.getLogin()==null || t.getLogin().trim().length()==0)
			throw new ValidatorException(Constants.fieldNotFilledProperly, "User login is NULL")
	}

}
