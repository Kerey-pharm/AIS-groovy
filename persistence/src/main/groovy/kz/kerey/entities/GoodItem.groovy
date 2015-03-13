package kz.kerey.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne

/**
 * Created by dmussa on 3/13/2015.
 */
@Entity
class GoodItem {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Long id;

    @ManyToOne
    GoodItem parent;

    @ManyToOne
    Good good;

    String barcode;
    String serial;
    Date initialDate;
    Date expireDate;
    Long initialBoxCount;
    Long initialCount;
    Long currentCount;

}
