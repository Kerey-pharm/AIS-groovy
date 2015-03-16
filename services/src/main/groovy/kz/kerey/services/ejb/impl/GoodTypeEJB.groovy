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
	
	void createGoodType(GoodTypeWrapper type) {
		def em
		try {
			em = emf.createEntityManager()
			def list = em.createQuery("from GoodType where lower(name)=:text1")
					.setParameter("text1", type.name.toLowerCase())
					.getResultList()
			if (list.size()>0)
				throw new ServicesException(Constants.objectExists, "GoodType with name:${type.name} exists")
			def obj = new GoodType(name: type.name)
			em.persist(obj)
		}
		finally {
			if (em!=null && em.isOpen())
                em.close()
		}
	}
	
	void deleteGoodType(Long id) {
		def em = null
		try {
			em = emf.createEntityManager()
			def obj = em.find(GoodType.class, id)
			if (obj==null)
				throw new ServicesException(Constants.objectIsNull, "GoodType with ID:${id} is NULL")
			em.remove(obj)
		}
		finally {
            if (em!=null && em.isOpen())
                em.close()
		}
	}
	
	List<GoodTypeWrapper> getGoodTypeList(Boolean paged, Integer pageNum, Integer perPage) {
		def em
		def result = new ArrayList<GoodTypeWrapper>()
		try {
			em = emf.createEntityManager()
			def query = em.createQuery("from GoodType order by name")
			if (paged) {
				query = query.setFirstResult(perPage * (pageNum - 1))
						.setMaxResults(perPage)
			}
			def list = query.getResultList()
			for(GoodType type in list) {
				result.add(new GoodTypeWrapper(id: type.id, name: type.name))
			}
		}
		finally {
            if (em!=null && em.isOpen())
                em.close()
		}
        result
	}

	void changeGoodTypeProperty(Long id, GoodTypeProperty property, String newValue) {
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
            if (em!=null && em.isOpen())
                em.close()
		}
	}
}