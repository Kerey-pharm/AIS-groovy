package kz.kerey.logic

import com.gmongo.GMongo
import groovy.json.JsonBuilder
import kz.kerey.entities.test.Group
import kz.kerey.entities.test.Person
import org.jongo.Jongo

class GroupDB {

    def static mongo = new GMongo()
    def static db = mongo.getDB("GroupsAndUsers")
    def static jongo = new Jongo(db)

    def static main(String[] args) {
        def groups = jongo.getCollection("groups")
        def group1 = new Group(name: "Group Number 3")
        def person1 = new Person(name: "Dauren", lastName: "Mussa", birthDate: new Date(), nickname: "Daka")
        group1.persons+=[person1]

        groups.insert(group1)

        groups.find().each {
            def printer = new JsonBuilder(it.as(Group))
            println printer.toPrettyString()
        }

    }

}
