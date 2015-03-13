package kz.kerey.validators

import kz.kerey.business.wrappers.GoodTypeWrapper
import kz.kerey.constants.Constants

class GoodTypeValidator extends Validator<GoodTypeWrapper> {

	static GoodTypeValidator validator = new GoodTypeValidator()
	static GoodTypeValidator getValidator() {
		validator
	}
	
	@Override
	void validate(GoodTypeWrapper t) throws ValidatorException {
		if (t==null) 
			throw new ValidatorException(Constants.objectIsNull, "objectIsNull")
		if (t.getName()==null || t.getName().trim().length()==0) 
			throw new ValidatorException(Constants.fieldNotFilledProperly, "goodTypeNameEmpty")
	}
	
}
