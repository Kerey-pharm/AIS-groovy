package kz.kerey.business.types.points

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("Warehouse")
class Warehouse extends Point {
}