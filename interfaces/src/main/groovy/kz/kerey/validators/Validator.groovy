package kz.kerey.validators

import java.io.Serializable

abstract class Validator<T extends Serializable> {
	
	abstract void validate(T t) throws ValidatorException
	
}