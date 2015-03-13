package kz.kerey.entities

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany

/**
 * Created by dmussa on 3/13/2015.
 */
@Entity
class Good {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToMany(fetch=FetchType.LAZY, targetEntity=GoodType.class)
    List<GoodType> types;

    String name;
    String primaryBarcode;
    Boolean partialSelling;
    Long countPerBox;
    Long countSellable;

}
