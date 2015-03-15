package kz.kerey.business.types

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class PageParam {

    Boolean paged
    Integer pageNum
    Integer perPage

}