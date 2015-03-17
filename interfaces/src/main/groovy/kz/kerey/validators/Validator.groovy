package kz.kerey.validators

trait Validator<T extends Serializable> {
	
	abstract def validate(T t) throws ValidatorException
	
}