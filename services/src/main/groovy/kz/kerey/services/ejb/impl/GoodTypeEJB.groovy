package kz.kerey.services.ejb.impl

import javax.annotation.Resource
import javax.ejb.Local

import kz.kerey.business.types.GoodType
import kz.kerey.business.types.enums.GoodTypeProperty
import kz.kerey.business.wrappers.GoodTypeWrapper
import kz.kerey.constants.Constants
import kz.kerey.exceptions.ServicesException

import javax.ejb.Stateless

@Stateless
@Local(GoodTypeEJB.class)
class GoodTypeEJB {

	@Resource(mappedName = "java:jboss/drugstoreEntityManagerFactory")
    def emf
	
	def createGoodType(GoodTypeWrapper type) {
		def em = emf.createEntityManager()
		try {
			def list = em.createQuery("from GoodType where lower(name)=:text1")
					.setParameter("text1", type.name.toLowerCase())
					.getResultList()
			if (list.size()>0)
				throw new ServicesException(Constants.objectExists, "GoodType with name:${type.name} exists")
			def obj = new GoodType(name: type.name)
			em.persist obj
		}
		finally {
			if (em!=null && em.isOpen())
                em.close()
		}
	}
	
	def deleteGoodType(Long id) {
		def em = null
		try {
			em = emf.createEntityManager()
			def obj = em.find(GoodType.class, id)
			if (obj==null)
				throw new ServicesException(Constants.objectIsNull, "GoodType with ID:${id} is NULL")
			em.remove obj
		}
		finally {
            if (em!=null && em.isOpen())
                em.close()
		}
	}
	
	def getGoodTypeList(Boolean paged, Integer pageNum, Integer perPage) {
		def em
		def result = []
		try {
			em = emf.createEntityManager()
			def query = em.createQuery("from GoodType order by name")
			if (paged) {
				query = query.setFirstResult(perPage * (pageNum - 1))
                        .setMaxResults(perPage)
			}
			def list = query.getResultList()
            list.each {
                result = result + [new GoodTypeWrapper(id: it.id, name: it.name)]
            }
		}
		finally {
            !(em!=null && em.isOpen()) ?: em.close()
		}
        result
	}

	def changeGoodTypeProperty(Long id, GoodTypeProperty property, String newValue) {
		def em = null
		try {
			em = emf.createEntityManager()
			def obj = em.find(GoodType.class, id)
			if (obj==null)
				throw new ServicesException(Constants.objectIsNull, "GoodType with ID:${id} is NULL")
			em.createQuery("update GoodType set ${property.toString()}=:value where id=:idValue")
				.setParameter("value", newValue)
				.setParameter("idValue", id)
				.executeUpdate()
		}
		finally {
            !(em!=null && em.isOpen()) ?: em.close()
		}
	}
}