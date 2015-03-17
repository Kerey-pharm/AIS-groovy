package kz.kerey.validators

import kz.kerey.business.wrappers.LocationWrapper
import kz.kerey.constants.Constants

@Singleton
class LocationValidator implements Validator<LocationWrapper> {

	def validate(LocationWrapper t) throws ValidatorException {
		if (t==null)
			throw new ValidatorException(Constants.objectIsNull, "Location is NULL")
		if (t.getName()==null || t.getName().trim().length()==0)
			throw new ValidatorException(Constants.fieldNotFilledProperly, "Location name is NULL or EMPTY")
	}

}
