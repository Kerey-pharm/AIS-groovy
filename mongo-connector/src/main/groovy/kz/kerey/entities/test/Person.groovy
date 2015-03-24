package kz.kerey.entities.test

import org.jongo.marshall.jackson.oid.ObjectId

class Person {

    @ObjectId
    String id
    String name
    String lastName
    String nickname
    Date birthDate

}
