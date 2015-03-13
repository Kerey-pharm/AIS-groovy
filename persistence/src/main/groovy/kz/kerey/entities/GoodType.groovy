package kz.kerey.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * Created by dmussa on 3/13/2015.
 */
@Entity
class GoodType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id

    String name

}
