package kz.kerey.validators

abstract class Validator<T extends Serializable> {
	
	abstract void validate(T t) throws ValidatorException
	
}