package kz.kerey.exceptions

import kz.kerey.constants.Constants

import javax.ejb.EJBException

class ServicesException extends EJBException {

    Constants errorCode
	String comment

}