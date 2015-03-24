package kz.kerey.entities.test

import org.jongo.marshall.jackson.oid.ObjectId

class Group {

    @ObjectId
    String id
    String name
    List<Person> persons = []

}
