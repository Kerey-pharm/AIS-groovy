package kz.kerey.validators

import kz.kerey.business.wrappers.UserWrapper
import kz.kerey.constants.Constants

@Singleton
class UserValidator implements Validator<UserWrapper> {

	def validate(UserWrapper t) throws ValidatorException {
		if (t==null)
			throw new ValidatorException(Constants.objectIsNull, "User object is NULL")
		if (t.getLogin()==null || t.getLogin().trim().length()==0)
			throw new ValidatorException(Constants.fieldNotFilledProperly, "User login is NULL")
	}

}
