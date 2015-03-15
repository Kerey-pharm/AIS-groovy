package kz.kerey.flow

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Step {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id

    @ManyToOne
    Ladder ladder

	String name
	String comment

}