package kz.kerey.flow

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.OrderColumn

@Entity
class Ladder {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id

    @ManyToMany
    @OrderColumn(name="order_index")
    List<Step> steps

    String name
    String comment
	Long executionTime

}