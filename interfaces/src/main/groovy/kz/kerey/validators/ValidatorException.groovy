package kz.kerey.validators

import kz.kerey.constants.Constants

class ValidatorException extends RuntimeException {

    Constants errorCode
    String comment

}