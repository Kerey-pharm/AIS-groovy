package kz.kerey.validators

import kz.kerey.business.wrappers.GoodTypeWrapper
import kz.kerey.constants.Constants

@Singleton
class GoodTypeValidator implements Validator<GoodTypeWrapper> {

	def validate(GoodTypeWrapper t) throws ValidatorException {
		if (t==null) 
			throw new ValidatorException(errorCode: Constants.objectIsNull, comment: "objectIsNull")
		if (t.getName()==null || t.getName().trim().length()==0) 
			throw new ValidatorException(errorCode: Constants.fieldNotFilledProperly, comment:  "goodTypeNameEmpty")
	}
	
}
