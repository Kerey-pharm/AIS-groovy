package kz.kerey.validators

import kz.kerey.business.wrappers.RoleWrapper
import kz.kerey.constants.Constants

@Singleton
class RoleValidator implements Validator<RoleWrapper> {

	def validate(RoleWrapper t) throws ValidatorException {
		if (t==null)
			throw new ValidatorException(Constants.objectIsNull, "Role object is null")
		if (t.getName()==null || t.getName().trim().length()==0)
			throw new ValidatorException(Constants.fieldNotFilledProperly, "Role name is null")
	}

}
